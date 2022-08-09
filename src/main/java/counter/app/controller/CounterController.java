package counter.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CounterController {

  private static final Logger logger = LoggerFactory.getLogger(CounterController.class);


  public static boolean started = true;
  public static long interval = 5000;

  @GetMapping("/start")
  public String startTask() {

    logger.trace("A TRACE Message");
    logger.debug("A DEBUG Message");
    logger.info("An INFO Message");
    logger.warn("A WARN Message");
    logger.error("An ERROR Message");

    log.info("i am from slf4j2");


    started = true;
    Runnable myThread = () -> {
      try {
        int i = 0;

        while (started) {
          Thread.sleep(interval);
          logger.info("logger count {}", i);
          i++;
        }

        if (!started)
          logger.info("stopped task.....at {}", i);
      } catch (Exception e) {
        logger.error("Error in loop {}", e.getMessage());
      }
    };

    Thread run = new Thread(myThread);
    run.start();
    return "task started again and agian";
  }

  @GetMapping("/stop")
  public String stopTask() {
    logger.info("stopping task.....a");
    started = false;
    return "stopping task...";
  }

  @GetMapping("/setInterval")
  public String setInterval(@RequestParam long interval) {
    logger.info("setting interval...{}", interval);
    CounterController.interval = interval;
    return "new interval set";
  }

}
