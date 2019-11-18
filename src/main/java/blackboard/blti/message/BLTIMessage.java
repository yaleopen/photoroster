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
package blackboard.blti.message;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.oauth.OAuthMessage;

/**
 * Represents a Basic LTI message.
 * <p>
 * Note, only the resourceLink's id is a required parameter, so other launch data may be null.
 * 
 * @author Jim Riecken <jriecken@blackboard.com>
 */
public class BLTIMessage implements Serializable
{
  private static final long serialVersionUID = 1L;
  
  private transient OAuthMessage msg;
  private String key;
  private String messageType;
  private String ltiVersion;
  private ResourceLink resourceLink = new ResourceLink();
  private User user = new User();
  private Context context = new Context();
  private LaunchPresentation launchPresentation = new LaunchPresentation();
  private ToolConsumerInfo toolConsumerInfo = new ToolConsumerInfo();
  private Map<String, String> customParameters = new HashMap<String, String>();

  /**
   * Create a new message using the specified consumer key.
   * <p>
   * This method should be used on the consumer-side.
   * 
   * @param key The consumer key to use.
   */
  public BLTIMessage( String key )
  {
    this.key = key;
    this.messageType = BLTIParameter.MESSAGE_TYPE_VALUE;
    this.ltiVersion = BLTIParameter.LTI_VERSION_VALUE;
  }

  /**
   * Create a new message based on an incoming OAuth message.
   * <p>
   * This method should be used on the producer-side.
   * 
   * @param msg The incoming message.
   */
  public BLTIMessage( OAuthMessage msg )
  {
    this.msg = msg;
    try
    {
      this.key = msg.getConsumerKey();
    }
    catch ( IOException e )
    {
      throw new RuntimeException();
    }
  }

  /*
   * This should only be called from the BLTIProducer when validating the message.
   */
  public OAuthMessage getOAuthMessage()
  {
    return msg;
  }

  public String getKey()
  {
    return key;
  }

  public String getMessageType()
  {
    return messageType;
  }

  public void setMessageType( String messageType )
  {
    this.messageType = messageType;
  }

  public String getLtiVersion()
  {
    return ltiVersion;
  }

  public void setLtiVersion( String ltiVersion )
  {
    this.ltiVersion = ltiVersion;
  }

  public ResourceLink getResourceLink()
  {
    return resourceLink;
  }

  public User getUser()
  {
    return user;
  }

  public Context getContext()
  {
    return context;
  }

  public LaunchPresentation getLaunchPresentation()
  {
    return launchPresentation;
  }

  public ToolConsumerInfo getToolConsumerInfo()
  {
    return toolConsumerInfo;
  }

  public Map<String, String> getCustomParameters()
  {
    return customParameters;
  }
}
