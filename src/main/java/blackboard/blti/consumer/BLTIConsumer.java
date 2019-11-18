/*
 * Copyright (C) 2011, Blackboard Inc.
 * 
 * All rights reserved. 
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 * 
 * -- Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 * disclaimer.
 * 
 * -- Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
 * following disclaimer in the documentation and/or other materials provided with the distribution.
 * 
 * -- Neither the name of Blackboard Inc. nor the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY BLACKBOARD INC ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL BLACKBOARD INC. BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package blackboard.blti.consumer;

import static blackboard.blti.message.BLTIParameter.*;

import blackboard.blti.message.*;
import blackboard.blti.util.StringUtil;

import java.util.*;

import net.oauth.*;

/**
 * For use in generating Basic LTI launches on the Tool Consumer side.
 * 
 * @author Jim Riecken <jriecken@blackboard.com>
 */
public class BLTIConsumer
{
  private OAuthMessage msg;
  private String key;

  /**
   * Create a new BLTIClient that will use the specified method, contact the specified url, and send the specified
   * message.
   * 
   * @param method The HTTP method this will be sent via (e.g. POST, GET)
   * @param url The URL to which this message will be sent.
   * @param msg The message to send.
   */
  public BLTIConsumer( String method, String url, BLTIMessage msg )
  {
    Map<String, String> parameters = new HashMap<String, String>();
    parameters.put( MESSAGE_TYPE, msg.getMessageType() );
    parameters.put( LTI_VERSION, msg.getLtiVersion() );

    ResourceLink rl = msg.getResourceLink();
    parameters.put( RESOURCE_LINK_ID, rl.getId() );
    setIfNotEmpty( parameters, RESOURCE_LINK_TITLE, rl.getTitle() );
    setIfNotEmpty( parameters, RESOURCE_LINK_DESCRIPTION, rl.getDescription() );
    User u = msg.getUser();
    setIfNotEmpty( parameters, USER_ID, u.getId() );
    setIfNotEmpty( parameters, USER_IMAGE, u.getImageUrl() );
    List<Role> roles = u.getRoles();
    if ( !roles.isEmpty() )
    {
      StringBuilder roleString = new StringBuilder();
      boolean first = true;
      for ( Role r : roles )
      {
        if ( !first )
        {
          roleString.append( "," );
        }
        roleString.append( r.getFull() );
        first = false;
      }
      parameters.put( ROLES, roleString.toString() );
    }
    setIfNotEmpty( parameters, LIS_PERSON_SOURCEDID, u.getLisSourcedId() );
    setIfNotEmpty( parameters, LIS_PERSON_NAME_GIVEN, u.getGivenName() );
    setIfNotEmpty( parameters, LIS_PERSON_NAME_FAMILY, u.getFamilyName() );
    setIfNotEmpty( parameters, LIS_PERSON_NAME_FULL, u.getFullName() );
    setIfNotEmpty( parameters, LIS_PERSON_CONTACT_EMAIL_PRIMARY, u.getEmail() );
    Context ctx = msg.getContext();
    setIfNotEmpty( parameters, CONTEXT_ID, ctx.getId() );
    setIfNotEmpty( parameters, CONTEXT_TYPE, ctx.getType() );
    setIfNotEmpty( parameters, CONTEXT_TITLE, ctx.getTitle() );
    setIfNotEmpty( parameters, CONTEXT_LABEL, ctx.getLabel() );
    setIfNotEmpty( parameters, LIS_COURSE_OFFERING_SOURCEDID, ctx.getLisCourseOfferingSourcedId() );
    setIfNotEmpty( parameters, LIS_COURSE_SECTION_SOURCEDID, ctx.getLisCourseSectionSourcedId() );
    LaunchPresentation pres = msg.getLaunchPresentation();
    setIfNotEmpty( parameters, LAUNCH_PRESENTATION_LOCALE, pres.getLocale() );
    setIfNotEmpty( parameters, LAUNCH_PRESENTATION_DOCUMENT_TARGET, pres.getDocumentTarget() );
    setIfNotEmpty( parameters, LAUNCH_PRESENTATION_HEIGHT, pres.getHeight() );
    setIfNotEmpty( parameters, LAUNCH_PRESENTATION_WIDTH, pres.getWidth() );
    setIfNotEmpty( parameters, LAUNCH_PRESENTATION_RETURN_URL, pres.getReturnUrl() );
    ToolConsumerInfo tci = msg.getToolConsumerInfo();
    setIfNotEmpty( parameters, TOOL_CONSUMER_INSTANCE_CONTACT_EMAIL, tci.getEmail() );
    setIfNotEmpty( parameters, TOOL_CONSUMER_INSTANCE_DESCRIPTION, tci.getDescription() );
    setIfNotEmpty( parameters, TOOL_CONSUMER_INSTANCE_GUID, tci.getGuid() );
    setIfNotEmpty( parameters, TOOL_CONSUMER_INSTANCE_NAME, tci.getName() );
    setIfNotEmpty( parameters, TOOL_CONSUMER_INSTANCE_URL, tci.getUrl() );
    Map<String, String> custom = msg.getCustomParameters();
    for ( Map.Entry<String, String> p : custom.entrySet() )
    {
      parameters.put( CUSTOM + p.getKey().toLowerCase().replaceAll( "[^a-zA-Z0-9]", "_" ), p.getValue() );
    }
    this.msg = new OAuthMessage( method, url, parameters.entrySet() );
    this.key = msg.getKey();
  }

  private void setIfNotEmpty( Map<String, String> params, String key, String value )
  {
    if ( StringUtil.notEmpty( value ) )
    {
      params.put( key, value );
    }
  }

  /**
   * Sign the message using the specified secret.
   * 
   * @param secret The secret to use when signing the message.
   */
  public void sign( String secret )
  {
    try
    {
      msg.addRequiredParameters( new OAuthAccessor( new OAuthConsumer( "about:blank", key, secret, null ) ) );
    }
    catch ( Exception e )
    {
      throw new RuntimeException( e );
    }
  }

  /**
   * Get the parameters that should be sent to the Basic LTI Tool Provider.
   * 
   * @return The parameters that should be sent to the Tool Provider.
   */
  public List<Map.Entry<String, String>> getParameters()
  {
    try
    {
      if ( StringUtil.notEmpty( key ) && StringUtil.isEmpty( msg.getParameter( OAUTH_CONSUMER_KEY ) ) )
      {
        msg.addParameter( OAUTH_CONSUMER_KEY, key );
      }
      return msg.getParameters();
    }
    catch ( Exception e )
    {
      throw new RuntimeException( e );
    }
  }
}
