/*
 * CODENVY CONFIDENTIAL
 * __________________
 *
 * [2012] - [2013] Codenvy, S.A.
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
 */

var USER_AGENT = navigator.userAgent.toLowerCase();
var IS_WS_SUPPORTED = ("WebSocket" in window);
if (!IS_WS_SUPPORTED || (USER_AGENT.indexOf("chrome") == -1 && USER_AGENT.indexOf("firefox") == -1 && USER_AGENT.indexOf("safari") == -1)) {
    if (document.body != null) {
        document.body.appendChild(createDivContainer());
    }

    function createDivContainer() {
        if (USER_AGENT.indexOf("msie 7") != -1 || USER_AGENT.indexOf("msie 8") != -1 || USER_AGENT.indexOf("msie 9") != -1) {
            var container = document.createElement('<div style="position:relative; height: 290px; margin: 20px auto 0; padding-top: 10px; width: 600px; text-align:center; font-family: Verdana, Bitstream Vera Sans, sans-serif; font-size:16px; border-top: 1px solid #DBDFE5; border-bottom: 1px solid #DBDFE5"></div>');
        }
        else {
            var container = document.createElement('div');
            container.setAttribute('style', 'position:relative; height: 290px; margin: 20px auto 0; padding-top: 10px; width: 600px; text-align:center; font-family: Verdana, Bitstream Vera Sans, sans-serif; font-size:16px; border-top: 1px solid #DBDFE5; border-bottom: 1px solid #DBDFE5');
        }
        container.innerHTML = '<div style="height: 50px; margin:0 auto; font-size:18px; color: #2C578A; font-weight: bold">It looks like you&#39;re using a browser that isn&#39;t supported.</div><div style="height: 50px; margin:0 auto; font-size:16px; color: #707070">To use Codenvy, upgrade to the latest one of these supported browsers.</div><div style="position: relative; height: 175px; background-color: #EFEFF5;"><div style="position: absolute; left: 65px; font-weight: bold;"><a href="http://www.google.com/chrome/" target="_blank" style="text-decoration: none; cursor: pointer;"><div style="width: 120px; height: 25px; padding-top: 130px; background: url(chrome.png) no-repeat; color: #2C578A;">Google</br>Chrome</div></a></div><div style="position: absolute; left: 234px; font-family: Verdana, Bitstream Vera Sans, sans-serif; font-weight: bold;"><a href="http://www.firefox.com" target="_blank" style="text-decoration: none; cursor: pointer;"><div style="width: 120px; height: 25px; padding-top: 130px; background: url(firefox.png) no-repeat; color: #2C578A;">Mozilla</br>Firefox</div></a></div><div style="position: absolute; left: 396px; font-weight: bold;"><a href="http://support.apple.com/downloads/#safari" target="_blank" style="text-decoration: none; cursor: pointer;"><div style="width: 120px; height: 25px; padding-top: 130px; background: url(safari.png) no-repeat; color: #2C578A;">Apple</br>Safari</div></a></div></div>';
        return container;
    }
}
