package com.finshine.springcloud.controller;

import com.finshine.springcloud.service.PaymentFeignClientService;
import com.finshine.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/*
 * @Author liuqinghe
 * @Date 2023/03/04 15:00
 **/
@RestController
public class OrderHystirxController {
    @Resource
    private PaymentHystrixService paymentHystrixService;
    @Resource
    private PaymentFeignClientService paymentFeignClientService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentInfo_OK(id);
        return result;
    }

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    @HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
    })
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentInfo_TimeOut(id);
        return result;
    }

    public String paymentTimeOutFallbackMethod(@PathVariable("id") Integer id) {
        return "我是消费者80,对方支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己,o(╥﹏╥)o";
    }

    @GetMapping("/consumer/payment/hystrix/{id}")
    public String paymentInfo(@PathVariable("id") Integer id) {
        String result = paymentFeignClientService.getPaymentInfo(id);
        return result;
    }
}
