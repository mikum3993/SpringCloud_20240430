// package com.mhmall.cart.client;
//
// import com.mhmall.cart.doamin.dto.ItemDTO;
// import org.springframework.cloud.openfeign.FeignClient;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;
//
// import java.util.Collection;
// import java.util.List;
//
// /**
//  * @program: SpringCloud_Study
//  * @description:
//  * @author: {}
//  * @create: 2024/5/3 18:16
//  */
// @FeignClient("item-service")
// public interface ItemClient {
//     @GetMapping("/items")
//     List<ItemDTO> queryItemByIds(@RequestParam("ids") Collection<Long> ids);
// }
