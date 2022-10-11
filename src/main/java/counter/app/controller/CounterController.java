package counter.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
  public ResponseEntity<String> greeting(@RequestHeader("Authorization") String headervl) {
    // code that uses the language variable
    return new ResponseEntity<String>(headervl, HttpStatus.OK);
  }

}
