canvas-photoroster
====

To Build: 

---

    mvn clean install

---

To Run:

    docker-compose up --build

Test photo roster :

1. Install in Canvas Test Instance
    
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
     <cartridge_basiclti_link xmlns="http://www.imsglobal.org/xsd/
     imslticc_v1p0"
         xmlns:blti = "http://www.imsglobal.org/xsd/imsbasiclti_v1p0"
         xmlns:lticm ="http://www.imsglobal.org/xsd/imslticm_v1p0"
         xmlns:lticp ="http://www.imsglobal.org/xsd/imslticp_v1p0"
         xmlns:xsi = "http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation = "http://www.imsglobal.org/xsd/imslticc_v1p0
     http://www.imsglobal.org/xsd/lti/ltiv1p0/imslticc_v1p0.xsd
         http://www.imsglobal.org/xsd/imsbasiclti_v1p0 http://
     www.imsglobal.org/xsd/lti/ltiv1p0/imsbasiclti_v1p0.xsd
         http://www.imsglobal.org/xsd/imslticm_v1p0 http://
     www.imsglobal.org/xsd/lti/ltiv1p0/imslticm_v1p0.xsd
         http://www.imsglobal.org/xsd/imslticp_v1p0 http://
     www.imsglobal.org/xsd/lti/ltiv1p0/imslticp_v1p0.xsd">
     <blti:launch_url>http://<HOST_IP>:8080/photoroster/launch</blti:launch_url>
         <blti:title>Photo Roster</blti:title>
         <blti:description>Photo directory of course participants</blti:description>
         <blti:extensions platform="canvas.instructure.com">
           <lticm:property name="privacy_level">public</lticm:property>
           <lticm:options name="course_navigation">
             <lticm:property name="default">enabled</lticm:property>
             <lticm:property name="visibility">admins</lticm:property>
             <lticm:property name="enabled">true</lticm:property>
           </lticm:options>
         </blti:extensions>
     </cartridge_basiclti_link>
     ```

To Shutdown:

    docker-compose down

   