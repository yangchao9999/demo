/**
 * 生产环境
 */
;(function () {
  window.SITE_CONFIG = {};

  // api接口请求地址
  window.SITE_CONFIG['baseUrl'] = 'http://exam-server.linghang-tech.com';
  window.SITE_CONFIG['channelUrl'] = 'http://exam-server-channel.linghang-tech.com';
  window.SITE_CONFIG['websocketUrl'] = 'ws://exam-websocket.linghang-tech.com:9989';

  // cdn地址 = 域名 + 版本号
  window.SITE_CONFIG['domain']  = './'; // 域名
  window.SITE_CONFIG['version'] = '';   // 版本号(年月日时分)
  window.SITE_CONFIG['cdnUrl']  = window.SITE_CONFIG.domain + window.SITE_CONFIG.version;
})();