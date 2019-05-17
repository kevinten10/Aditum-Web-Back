package com.ten.aditum.back.personas.model;

import com.ten.aditum.back.BaseAnalysor;
import com.ten.aditum.back.entity.AccessAddress;
import com.ten.aditum.back.entity.AccessTime;
import com.ten.aditum.back.entity.Person;
import com.ten.aditum.back.entity.PersonasLabel;
import com.ten.aditum.back.service.AccessAddressService;
import com.ten.aditum.back.service.AccessTimeService;
import com.ten.aditum.back.vo.Personas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 随机标签生成
 */
@Slf4j
@Component
@EnableScheduling
@EnableAutoConfiguration
public class AnalysorZero extends BaseAnalysor {

    @Override
    public void showModelLabel() {
        PersonasLabel label1 = new PersonasLabel()
                .setLabelId("random1")
                .setLabelType(0)
                .setLabelName("作息规律")
                .setLabelDesc("随机生成，生成概率每天10个人");
        PersonasLabel label2 = new PersonasLabel()
                .setLabelId("random2")
                .setLabelType(0)
                .setLabelName("生活健康")
                .setLabelDesc("随机生成，生成概率每天10个人");
        PersonasLabel label3 = new PersonasLabel()
                .setLabelId("random3")
                .setLabelType(0)
                .setLabelName("旅游爱好者")
                .setLabelDesc("随机生成，生成概率每天10个人");
    }

//    @Scheduled(cron = TEST_TIME)

    /**
     * 每天5点10分执行
     */
    @Scheduled(cron = "0 0 10 5 1/1 * ?")
    public void analysis() {
        log.info("随机标签生成...开始");

        List<Person> personList = selectAllPerson();

        // 作息规律
        for (int i = 0; i < 10; i++) {
            int index = (int) (Math.random() * personList.size());
            analysisPerson(personList.get(index), "random1");
        }
        // 生活健康
        for (int i = 0; i < 10; i++) {
            int index = (int) (Math.random() * personList.size());
            analysisPerson(personList.get(index), "random2");
        }
        // 旅游爱好者
        for (int i = 0; i < 10; i++) {
            int index = (int) (Math.random() * personList.size());
            analysisPerson(personList.get(index), "random3");
        }

        log.info("随机标签生成...结束");
    }

    private void analysisPerson(Person person, String label) {
        Personas personas = new Personas()
                .setPersonnelId(person.getPersonnelId())
                .setLabelId(label);
        personasController.updatePersonas(personas);
        log.info("用户 {} 随机标签计算完成，{}", person.getPersonnelName(), label);
    }

}
