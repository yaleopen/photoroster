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

import java.io.Serializable;

/**
 * Represents a user role in a Basic LTI message
 * 
 * @author Jim Riecken <jriecken@blackboard.com>
 */
public class Role implements Serializable
{
  private static final long serialVersionUID = 1L;
  
  public static final String SYSTEM_NAMESPACE = "urn:lti:sysrole:ims/lis/";
  public static final String INSTITUTION_NAMESPACE = "urn:lti:instrole:ims/lis/";
  public static final String CONTEXT_NAMESPACE = "urn:lti:role:ims/lis/";

  public static final String LEARNER = "Learner";
  public static final String INSTRUCTOR = "Instructor";
  public static final String CONTENT_DEVELOPER = "ContentDeveloper";
  public static final String MEMBER = "Member";
  public static final String MANAGER = "Manager";
  public static final String MENTOR = "Mentor";
  public static final String ADMINISTRATOR = "Administrator";
  public static final String TEACHING_ASSISTANT = "TeachingAssistant";
  
  private String handle;
  private String namespace;

  /**
   * Create a role based on a raw role value. This can be a fully-qualified URN for a role, or just the name of a
   * Context role.
   * 
   * @param raw The raw role.
   */
  public Role( String raw )
  {
    if ( raw.startsWith( "urn:" ) )
    {
      int idx = raw.lastIndexOf( "/" );
      namespace = raw.substring( 0, idx + 1 );
      handle = raw.substring( idx + 1 );
    }
    else
    {
      namespace = CONTEXT_NAMESPACE;
      handle = raw;
    }
  }

  public String getHandle()
  {
    return handle;
  }

  public void setHandle( String handle )
  {
    this.handle = handle;
  }

  public String getNamespace()
  {
    return namespace;
  }

  public void setNamespace( String namespace )
  {
    this.namespace = namespace;
  }

  public String getFull()
  {
    return namespace + handle;
  }
}
