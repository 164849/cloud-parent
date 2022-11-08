package com.itck.qindao.controller;

import com.itck.qindao.service.CheckInService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("checkin")
@RequiredArgsConstructor
public class CheckInController {

    private final CheckInService checkInService;

    /**
     * 用户签到接口
     *
     * @param userId 用户ID
     * @return {@link String}
     */
    @PostMapping("/{userId}")
    public String checkIn(@PathVariable(name = "userId") Long userId) {
        checkInService.checkIn(userId);
        return "SUCCESS";
    }

    /**
     * 统计指定日期的签到人数
     *
     * @param date 日期
     * @return {@link Long} 签到的人数
     */
    @GetMapping("/count")
    public Long countDateCheckIn(String date) {
        return checkInService.countDateCheckIn(date);
    }

    /**
     * 获取指定用户在某个时间段内的签到次数
     *
     * @param userId    用户id
     * @param startDate 统计的开始时间
     * @param endDate   统计的结束时间
     * @return {@link Long} 签到次数
     */
    @GetMapping("/{userId}")
    public Long countCheckIn(@PathVariable(name = "userId") Long userId,
                             @RequestParam(name = "startDate") String startDate,
                             @RequestParam(name = "endDate") String endDate) {
        return checkInService.countCheckIn(userId, startDate, endDate);
    }

    /**
     * 获取某个用户连续签到次数
     *
     * @param userId 用户id
     * @return long 连续签到次数
     */
    @GetMapping("/continuousdays/{userId}")
    public long getContinuousCheckIn(@PathVariable(name = "userId") Long userId) {
        return checkInService.getContinuousCheckIn(userId);
    }
}
