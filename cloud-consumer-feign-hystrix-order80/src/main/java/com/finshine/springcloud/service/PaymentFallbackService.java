package com.finshine.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * @Author liuqinghe
 * @Date 2023/07/05 13:41
 * @Description
 */
@Component
public class PaymentFallbackService implements PaymentFeignClientService {
    @Override
    public String getPaymentInfo(Integer id) {
        return "服务调用失败，提示来自：cloud-consumer-feign-order80";
    }
}
