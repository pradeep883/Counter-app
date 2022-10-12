package counter.app.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin
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
  private ResponseEntity<?> getRequestInformation(@RequestHeader Map<String, String> headers,
      HttpEntity<String> httpEntity) {

    String json = httpEntity.getHeaders().getFirst("Authorization");


    List<Object> resultList = new ArrayList<>();
    ModelMap map = new ModelMap();
    headers.forEach((key, value) -> {
      map.addAttribute(key, value);
    });
    resultList.add(map);
    resultList.add(json);
    return new ResponseEntity<>(resultList, HttpStatus.OK);
  }

  @GetMapping("/header2")
  private ResponseEntity<?> getRequestInformation2(HttpServletRequest servletRequest) {

    List<Object> resultList = new ArrayList<>();
    HttpServletRequest req =
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    Collections.list(req.getHeaderNames()).stream().forEach(hdr -> resultList.add(hdr));
    return new ResponseEntity<>(resultList, HttpStatus.OK);
  }
}
