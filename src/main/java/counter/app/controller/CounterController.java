package counter.app.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CounterController {

  public static boolean started = true;
  public static long interval = 5000;

  @GetMapping("/start")
  public String startTask() {

    log.trace("A TRACE Message");
    log.debug("A DEBUG Message");
    log.info("An INFO Message");
    log.warn("A WARN Message");
    log.error("An ERROR Message");
    log.info("i am from slf4j2");


    started = true;
    Runnable myThread = () -> {
      try {
        int i = 0;

        while (started) {
          Thread.sleep(interval);
          log.info("log count {}", i);
          i++;
        }

        if (!started)
          log.info("stopped task.....at {}", i);
      } catch (Exception e) {
        log.error("Error in loop {}", e.getMessage());
      }
    };

    Thread run = new Thread(myThread);
    run.start();
    return "task started";
  }

  @GetMapping("/stop")
  public String stopTask() {
    log.info("stopping task.....a");
    started = false;
    return "stopping task...";
  }

  @GetMapping("/setInterval")
  public String setInterval(@RequestParam long interval) {
    log.info("setting interval...{}", interval);
    CounterController.interval = interval;
    return "new interval set";
  }



  @GetMapping("/header")
  private Map<String, String> getRequestInformation(HttpServletRequest request) {
    Map<String, String> map = new HashMap<String, String>();
    Enumeration headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String key = (String) headerNames.nextElement();
      String value = request.getHeader(key);
      map.put("header: " + key, value);
    }
    Enumeration parameterNames = request.getParameterNames();
    while (parameterNames.hasMoreElements()) {
      String key = (String) parameterNames.nextElement();
      String value = request.getParameter(key);
      map.put("parameter: " + key, value);
    }

    while (parameterNames.hasMoreElements()) {
      String key = (String) parameterNames.nextElement();
      String value = request.getParameter(key);
      map.put("parameter: " + key, value);
    }
    map.put("getRemoteUser", request.getRemoteUser());
    map.put("getMethod", request.getMethod());
    map.put("getQueryString", request.getQueryString());
    map.put("getAuthType", request.getAuthType());
    map.put("getContextPath", request.getContextPath());
    map.put("getPathInfo", request.getPathInfo());
    map.put("getPathTranslated", request.getPathTranslated());
    map.put("getRequestedSessionId", request.getRequestedSessionId());
    map.put("getRequestURI", request.getRequestURI());
    map.put("getRequestURL", request.getRequestURL().toString());
    map.put("getMethod", request.getMethod());
    map.put("getServletPath", request.getServletPath());
    map.put("getContentType", request.getContentType());
    map.put("getLocalName", request.getLocalName());
    map.put("getProtocol", request.getProtocol());
    map.put("getRemoteAddr", request.getRemoteAddr());
    map.put("getServerName", request.getServerName());
    return map;
  }

}
