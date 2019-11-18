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

/**
 * Contains constants defining all of the Basic LTI request parameters.
 * 
 * @author Jim Riecken <jriecken@blackboard.com>
 */
public interface BLTIParameter
{
  String MESSAGE_TYPE = "lti_message_type";
  String MESSAGE_TYPE_VALUE = "basic-lti-launch-request";
  String LTI_VERSION = "lti_version";
  String LTI_VERSION_VALUE = "LTI-1p0";

  String OAUTH_CONSUMER_KEY = "oauth_consumer_key";

  String RESOURCE_LINK_ID = "resource_link_id";
  String RESOURCE_LINK_TITLE = "resource_link_title";
  String RESOURCE_LINK_DESCRIPTION = "resource_link_description";
  String USER_ID = "user_id";
  String USER_IMAGE = "user_image";
  String ROLES = "roles";
  String LIS_PERSON_SOURCEDID = "lis_person_sourcedid";
  String LIS_PERSON_NAME_GIVEN = "lis_person_name_given";
  String LIS_PERSON_NAME_FAMILY = "lis_person_name_family";
  String LIS_PERSON_NAME_FULL = "lis_person_name_full";
  String LIS_PERSON_CONTACT_EMAIL_PRIMARY = "lis_person_contact_email_primary";
  String LIS_COURSE_OFFERING_SOURCEDID = "lis_course_offering_sourcedid";
  String LIS_COURSE_SECTION_SOURCEDID = "lis_course_section_sourcedid";
  String LIS_RESULT_SOURCEDID = "lis_result_sourcedid";
  String CONTEXT_ID = "context_id";
  String CONTEXT_TYPE = "context_type";
  String CONTEXT_TITLE = "context_title";
  String CONTEXT_LABEL = "context_label";
  String LAUNCH_PRESENTATION_LOCALE = "launch_presentation_locale";
  String LAUNCH_PRESENTATION_DOCUMENT_TARGET = "launch_presentation_document_target";
  String LAUNCH_PRESENTATION_WIDTH = "launch_presentation_width";
  String LAUNCH_PRESENTATION_HEIGHT = "launch_presentation_height";
  String LAUNCH_PRESENTATION_RETURN_URL = "launch_presentation_return_url";
  String TOOL_CONSUMER_INSTANCE_GUID = "tool_consumer_instance_guid";
  String TOOL_CONSUMER_INSTANCE_NAME = "tool_consumer_instance_name";
  String TOOL_CONSUMER_INSTANCE_DESCRIPTION = "tool_consumer_instance_description";
  String TOOL_CONSUMER_INSTANCE_URL = "tool_consumer_instance_url";
  String TOOL_CONSUMER_INSTANCE_CONTACT_EMAIL = "tool_consumer_instance_contact_email";
  String CUSTOM = "custom_";
}
