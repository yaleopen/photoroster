/*
 * Copyright (C) 2011, Blackboard Inc.
 * 
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:
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
import java.util.ArrayList;
import java.util.List;

/**
 * Represents information sent about the current user in the Basic LTI message.
 * 
 * @author Jim Riecken <jriecken@blackboard.com>
 */
public class User implements Serializable
{
  private static final long serialVersionUID = 1L;
  
  private String id;
  private String lisSourcedId;
  private String imageUrl;
  private String givenName;
  private String familyName;
  private String fullName;
  private String email;
  private List<Role> roles = new ArrayList<Role>();

  public String getId()
  {
    return id;
  }

  public void setId( String id )
  {
    this.id = id;
  }

  public String getLisSourcedId()
  {
    return lisSourcedId;
  }

  public void setLisSourcedId( String lisSourcedId )
  {
    this.lisSourcedId = lisSourcedId;
  }

  public String getImageUrl()
  {
    return imageUrl;
  }

  public void setImageUrl( String imageUrl )
  {
    this.imageUrl = imageUrl;
  }

  public String getGivenName()
  {
    return givenName;
  }

  public void setGivenName( String givenName )
  {
    this.givenName = givenName;
  }

  public String getFamilyName()
  {
    return familyName;
  }

  public void setFamilyName( String familyName )
  {
    this.familyName = familyName;
  }

  public String getFullName()
  {
    return fullName;
  }

  public void setFullName( String fullName )
  {
    this.fullName = fullName;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail( String email )
  {
    this.email = email;
  }

  public List<Role> getRoles()
  {
    return roles;
  }

  public void addRole( Role role )
  {
    roles.add( role );
  }

  public void setRoles( List<Role> roles )
  {
    this.roles = roles;
  }

  // Convenience methods

  /**
   * Get whether the user has the specified role.
   * 
   * @param namespace The namespace of the role (see the namespace constants in {@link Role}).
   * @param handle The handle of the role.
   * @return Whether the user has the specified role.
   */
  public boolean isInRole( String namespace, String handle )
  {
    for ( Role r : roles )
    {
      if ( r.getNamespace().equals( namespace ) && r.getHandle().contains( handle ) )
      {
        return true;
      }
    }
    return false;
  }

  public boolean isLearner()
  {
    return isInRole( Role.CONTEXT_NAMESPACE, Role.LEARNER );
  }

  public boolean isInstructor()
  {
    return isInRole( Role.CONTEXT_NAMESPACE, Role.INSTRUCTOR );
  }

  public boolean isContentDeveloper()
  {
    return isInRole( Role.CONTEXT_NAMESPACE, Role.CONTENT_DEVELOPER );
  }

  public boolean isMember()
  {
    return isInRole( Role.CONTEXT_NAMESPACE, Role.MEMBER );
  }

  public boolean isManager()
  {
    return isInRole( Role.CONTEXT_NAMESPACE, Role.MANAGER );
  }

  public boolean isAdministrator()
  {
    return isInRole( Role.CONTEXT_NAMESPACE, Role.ADMINISTRATOR );
  }

  public boolean isTeachingAssistant()
  {
    return isInRole( Role.CONTEXT_NAMESPACE, Role.TEACHING_ASSISTANT );
  }
}
