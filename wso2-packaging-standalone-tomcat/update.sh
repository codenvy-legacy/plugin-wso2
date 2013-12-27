filename=`ls target | grep codenvy`
SSH_KEY_NAME=idex
SSH_AS_USER_NAME=cl-server
AS_IP=wso2preview.codenvy-dev.com
home=/home/cl-server/wso2-tomcat

deleteFileIfExists() {
    if [ -f $1 ]; then
        echo $1
        rm -rf $1
    fi
}

    echo "uploading new tomcat..."
    scp -i ~/.ssh/${SSH_KEY_NAME} target/${filename} ${SSH_AS_USER_NAME}@${AS_IP}:/home/cl-server/
    echo "stoping tomcat"
    ssh -i ~/.ssh/${SSH_KEY_NAME} ${SSH_AS_USER_NAME}@${AS_IP} "cd ${home}/bin/;if [ -f catalina.sh ]; then ./catalina.sh stop -force; fi"
    echo "clean up"
    ssh -i ~/.ssh/${SSH_KEY_NAME} ${SSH_AS_USER_NAME}@${AS_IP} "rm -rf ${home}/*"
    echo "unpacking new tomcat..."
    ssh -i ~/.ssh/${SSH_KEY_NAME} ${SSH_AS_USER_NAME}@${AS_IP} "mv /home/cl-server/${filename} ${home}"
    ssh -i ~/.ssh/${SSH_KEY_NAME} ${SSH_AS_USER_NAME}@${AS_IP} "cd ${home} && unzip ${filename}"
#    echo "install deps..."
#    ssh -i ~/.ssh/${SSH_KEY_NAME} ${SSH_AS_USER_NAME}@${AS_IP} "cd ${home}/ide;./install.sh /home/cl-server/.m2/repository"
    echo "starting new tomcat... on ${AS_IP}"
    ssh -i ~/.ssh/${SSH_KEY_NAME} ${SSH_AS_USER_NAME}@${AS_IP} "cd ${home}/bin;./catalina.sh start"

    AS_STATE='Starting'
    testfile=/tmp/catalina.out
    while [[ "${AS_STATE}" != "Started" ]]; do

    deleteFileIfExists ${testfile}

    scp -i ~/.ssh/${SSH_KEY_NAME} ${SSH_AS_USER_NAME}@${AS_IP}:${home}/logs/catalina.out ${testfile}

      if grep -Fq "Server startup" ${testfile}
        then
         echo "Tomcat of application server started"
         AS_STATE=Started
      fi

         echo "AS state = ${AS_STATE}  Attempt ${COUNTER}"
         sleep 5
         let COUNTER=COUNTER+1
         deleteFileIfExists ${testfile}
    done
