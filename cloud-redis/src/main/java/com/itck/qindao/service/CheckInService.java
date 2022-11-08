package com.itck.qindao.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class CheckInService {

    // 用户签到的key的前缀
    private static final String CHECK_IN_PRE_KEY = "USER_CHECKIN:DAY:";

    private static final String CONTINUOUS_CHECK_IN_COUNT_PRE_KEY = "USER_CHECKIN:CONTINUOUS_COUNT:";

    // 日期格式化器
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 用户签到
     *
     * @param userId 用户ID
     */
    public void checkIn(Long userId) {
        String today = LocalDate.now().format(DATE_TIME_FORMATTER);
        // 判断用户是否签到过
        if (isCheckIn(userId, today)) {
            return;
        }
        // 用户签到
        stringRedisTemplate.opsForValue().setBit(getCheckInKey(today), userId, true);
        // 更新连续签到天数
        updateContinuousCheckIn(userId);
    }


    /**
     * 检查用户是否签到
     *
     * @param userId 用户ID
     * @param date   签到日期
     * @return 如果签到过返回true, 否则返回false
     */
    public boolean isCheckIn(Long userId, String date) {
        Boolean isCheckIn = stringRedisTemplate.opsForValue().getBit(getCheckInKey(date), userId);
        return Optional.ofNullable(isCheckIn).orElse(false);
    }

    /**
     * 统计特定日期签到总人数
     * bitcount key
     *
     * @param date
     * @return
     */
    public Long countDateCheckIn(String date) {
        byte[] key = getCheckInKey(date).getBytes();
        Long result = stringRedisTemplate.execute((RedisCallback<Long>) connection -> connection.bitCount(key));
        return Optional.ofNullable(result).orElse(0L);
    }

    /**
     * 获取用户某个时间段签到次数
     *
     * @param userId    用户ID
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 签到次数
     */
    public Long countCheckIn(Long userId, String startDate, String endDate) {
        // 统计的开始时间
        LocalDate startLocalDate = LocalDate.parse(startDate, DATE_TIME_FORMATTER);
        // 统计的结束时间
        LocalDate endLocalDate = LocalDate.parse(endDate, DATE_TIME_FORMATTER);
        // 初始化签到次数
        AtomicLong count = new AtomicLong(0);
        long distance = Period.between(startLocalDate, endLocalDate).get(ChronoUnit.DAYS);
        if (distance < 0) {
            return count.get();
        }
        Stream.iterate(startLocalDate, d -> d.plusDays(1)).limit(distance + 1).forEach((LocalDate date) -> {
            // 获取给定的时间段中每一天是否签到
            Boolean isCheckIn = stringRedisTemplate.opsForValue().
                    getBit(getCheckInKey(date.format(DATE_TIME_FORMATTER)), userId);
            if (isCheckIn) {
                // 如果签到了，签到天数+1
                count.incrementAndGet();
            }
        });
        return count.get();
    }

    /*public static void main(String[] args) {
        // 1..100    1 2 3 4 ..
        // List<Integer> list = Stream.iterate(1, b -> b + 2).limit(10).collect(Collectors.toList());
        // list.forEach(System.out::println);
        // Stream.iterate("1",s->s+"0").limit(10).forEach(System.out::println);
        // List<String> collect = Stream.iterate("1", s -> s + "0").limit(10).collect(Collectors.toList());

        // Stream.iterate(LocalDate.now(),date->date.plusDays(1)).limit(30).forEach(System.out::println);

        long distance = Period.between(LocalDate.now(), LocalDate.now().plusDays(10)).get(ChronoUnit.DAYS);
        System.out.println(distance);
    }*/

    /**
     * 首先说一个两个参数的iterate，第一个参数是新流中的初始元素，然后使用该数据做第二个参数也就是UnaryOperator函数的入参去计算第二元素，然后把新计算得到的第二个元素作为入参继续计算第三个元素，以此循环可以制造无限个元素，在实际使用中一般使用limit（n）方法去获取包含n个的流。
     *
     * void stream_iterate() {
     *    Stream.iterate("1",  b -> b+"0").limit(3).forEach(System.out::println);
     * }
     * 运行结果：
     * 1
     * 10
     * 100
     */

    /**
     * 更新用户连续签到天数：+1
     *
     * @param userId
     */
    public void updateContinuousCheckIn(Long userId) {
        // 获取用户连续签到天数的key
        String key = getContinuousCheckInKey(userId);
        // val 当前连续签到了多少天
        String val = stringRedisTemplate.opsForValue().get(key);
        // count 连续签到的次数
        long count = 0;
        if (val != null) {
            count = Long.parseLong(val);
        }
        count++;
        // 设置连续签到天数
        stringRedisTemplate.opsForValue().set(key, String.valueOf(count));
        // 设置第二天过期
        // 设置固定的时间点key过期
        LocalDateTime time = LocalDateTime.now().plusDays(2).withHour(0).withMinute(0).withSecond(0);
        stringRedisTemplate.expireAt(key, time.toInstant(ZoneOffset.of("+8")));
    }


    /**
     * 获取用户连续签到天数
     *
     * @param userId
     * @return
     */
    public Long getContinuousCheckIn(Long userId) {
        String key = getContinuousCheckInKey(userId);
        String val = stringRedisTemplate.opsForValue().get(key);
        if (val == null) {
            return 0L;
        }
        return Long.parseLong(val);
    }

    // 拼接key
    private String getCheckInKey(String date) {
        return CHECK_IN_PRE_KEY + date;
    }

    private String getContinuousCheckInKey(Long userId) {
        return CONTINUOUS_CHECK_IN_COUNT_PRE_KEY + userId;
    }
}
