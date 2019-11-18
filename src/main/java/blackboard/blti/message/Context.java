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

import blackboard.blti.util.StringUtil;

import java.io.Serializable;

/**
 * Represents information about the Basic LTI context parameters.
 * 
 * @author Jim Riecken <jriecken@blackboard.com>
 */
public class Context implements Serializable
{
  private static final long serialVersionUID = 1L;
  
  public static final String CONTEXT_NAMESPACE = "urn:lti:context-type:ims/lis/";

  public static final String TYPE_TEMPLATE = "CourseTemplate";
  public static final String TYPE_OFFERING = "CourseOffering";
  public static final String TYPE_SECTION = "CourseSection";
  public static final String TYPE_GROUP = "Group";

  private String id;
  private String typeNamespace;
  private String typeHandle;
  private String title;
  private String label;
  private String lisCourseOfferingSourcedId;
  private String lisCourseSectionSourcedId;

  public String getId()
  {
    return id;
  }

  public void setId( String id )
  {
    this.id = id;
  }

  public void setType( String type )
  {
    if ( StringUtil.notEmpty( type ) )
    {
      if ( type.startsWith( "urn:" ) )
      {
        int idx = type.lastIndexOf( "/" );
        typeNamespace = type.substring( 0, idx + 1 );
        typeHandle = type.substring( idx + 1 );
      }
      else
      {
        typeNamespace = CONTEXT_NAMESPACE;
        typeHandle = type;
      }
    }
  }

  public String getTypeNamespace()
  {
    return typeNamespace;
  }

  public String getTypeHandle()
  {
    return typeHandle;
  }

  public String getType()
  {
    return typeNamespace + typeHandle;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle( String title )
  {
    this.title = title;
  }

  public String getLabel()
  {
    return label;
  }

  public void setLabel( String label )
  {
    this.label = label;
  }

  public String getLisCourseOfferingSourcedId()
  {
    return lisCourseOfferingSourcedId;
  }

  public void setLisCourseOfferingSourcedId( String lisCourseOfferingSourcedId )
  {
    this.lisCourseOfferingSourcedId = lisCourseOfferingSourcedId;
  }

  public String getLisCourseSectionSourcedId()
  {
    return lisCourseSectionSourcedId;
  }

  public void setLisCourseSectionSourcedId( String lisCourseSectionSourcedId )
  {
    this.lisCourseSectionSourcedId = lisCourseSectionSourcedId;
  }
}
