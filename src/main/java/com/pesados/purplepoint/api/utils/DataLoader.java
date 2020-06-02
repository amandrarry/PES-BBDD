package com.pesados.purplepoint.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import com.pesados.purplepoint.api.PurplePointApplication;
import com.pesados.purplepoint.api.model.alarm.Alarm;
import com.pesados.purplepoint.api.model.alarm.AlarmService;
import com.pesados.purplepoint.api.model.device.Device;
import com.pesados.purplepoint.api.model.device.DeviceService;
import com.pesados.purplepoint.api.model.image.Image;
import com.pesados.purplepoint.api.model.image.ImageService;
import com.pesados.purplepoint.api.model.location.Location;
import com.pesados.purplepoint.api.model.location.LocationService;
import com.pesados.purplepoint.api.model.report.Report;
import com.pesados.purplepoint.api.model.report.ReportService;
import com.pesados.purplepoint.api.model.user.User;
import com.pesados.purplepoint.api.model.user.UserService;

@Configuration
class LoadDatabase {
	private static final Logger logger = LoggerFactory.getLogger(PurplePointApplication.class);

    @Bean
  CommandLineRunner initImageDatabase(ImageService service) throws IOException {
      logger.info("Finding resource \"sample.jpg\"");
      Resource resource = new ClassPathResource("sample.jpg");
      logger.info("Converting resource \"sample.jpg\" to byte array");
      InputStream inputStream = resource.getInputStream();
      byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
      logger.info("Saving image in \"sample.jpg\" into DB");

	  return args -> {
      logger.info("Preloading " + service.saveImage(new Image("sample.jpg","image/jpg", Base64.getEncoder().encodeToString(bdata))));
    };
  }


    @Bean
  CommandLineRunner initUserDatabase(UserService service) {
	  
    return args -> {
      logger.info("Preloading " + service.saveUser(new User("test", "test1","isma@gmail.com", "1234", "others")));
      logger.info("Preloading " + service.saveUser(new User("Bilbo Baggins", "Bilbo1","testmail1@gmail.com", "1234", "female")));
      logger.info("Preloading " + service.saveUser(new User("Frodo Baggins","Frodo1" , "testmail2@gmail.com", "5678", "male")));
    };
  }
    
    @Bean
    CommandLineRunner initMapDatabase(ReportService service) {
      return args -> {
        logger.info("Preloading " + service.saveReport(new Report((new Location((float)41.447615, (float)2.224420, (float)100, (float)0)), new User("tonto1", "tonto1@mail.com"))));
        logger.info("Preloading " + service.saveReport(new Report((new Location((float)41.415026309656994, (float)2.151603698730469, (float)-1, (float)-1)), new User("tonto2", "tonto2@mail.com"))));
        logger.info("Preloading " + service.saveReport(new Report((new Location((float)41.425597961179896, (float) 2.1958923339843754, (float)-1, (float)-1)), new User("tonto3", "tonto3@mail.com"))));
        logger.info("Preloading " + service.saveReport(new Report((new Location((float)41.39993938849367, (float)2.151260375976563, (float)-1, (float)-1)), new User("tonto4", "tont4o@mail.com"))));
      };
    }


    @Bean
    CommandLineRunner initLocationDatabase(LocationService service) {
        User usr = new User("test5", "test5","testingthis@gmail.com", "1234", "others");

        return args -> {
            logger.info("Preloading " + service.saveLocation(new Location((float)41.447612, (float)2.224417, (float)100, (float)0)));
            logger.info("Preloading " + service.saveLocation(new Location((float)41.447379, (float)2.226842, (float)100, (float)0)));
            logger.info("Preloading " + service.saveLocation(new Location((float)21.160510, (float)-86.842466, (float)100, (float)0)));
            logger.info("Preloading " + service.saveLocation(new Location((float)41.402899, (float)2.121561, (float)100, (float)0)));
        };
    }

    @Bean
    CommandLineRunner initAlarmDatabase(AlarmService service) {
        return args -> {
            // Alarma "Mi Casa"
            logger.info("Preloading " + service.saveAlarm(new Alarm("isma", "1", new Location((float)41.447612, (float)2.224417, (float)100, (float)0))));
            // Alarma "bar"
            logger.info("Preloading " + service.saveAlarm(new Alarm("isma", "1", new Location((float)41.447379, (float)2.226842, (float)100, (float)0))));
            // Alarma "bar"
            logger.info("Preloading " + service.saveAlarm(new Alarm("isma", "1", new Location((float)21.160510, (float)-86.842466, (float)100, (float)0))));
            // Alarma "bar"
            logger.info("Preloading " + service.saveAlarm(new Alarm("isma", "1", new Location((float)41.402899, (float)2.121561, (float)100, (float)0))));
        };
    }

    @Bean
    CommandLineRunner initDeviceDatabase(DeviceService service) {
        return args -> {
            // Location "Mi Casa"
            logger.info("Preloading " + service.saveDevice(new Device("f2EJYEQeYyYq-v2ubvL7x5:APA91bFam-no_lk9-kryCZol_dXDEtRjyd_iyAORuLDuLgLmyblUhYE9sYV1Prj4ohxnt6-EM_tDBVOkhnV08e2szqCGjNBRap5vnRwzBVf0iCMzlCphZiAWCkRWiDx0pB71dZEj2Ej5", new Location((float)41.447612, (float)2.224417, 100, 0), new User())));
            // Location "bar"
            logger.info("Preloading " + service.saveDevice(new Device("2", new Location((float)41.447379, (float)2.226842, (float)100, (float)0), new User())));
            // Location "china"
            logger.info("Preloading " + service.saveDevice(new Device("3", new Location((float)21.160510, (float)-86.842466, (float)100, (float)0), new User())));
            // Location "otrositio"
            logger.info("Preloading " + service.saveDevice(new Device("4", new Location((float)41.402899, (float)2.121561, (float)100, (float)0), new User())));
        };
    }
}