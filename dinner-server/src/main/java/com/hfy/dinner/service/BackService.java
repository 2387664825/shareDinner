package com.hfy.dinner.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hfy.dinner.dao.BackDao;
import com.hfy.dinner.repository.dto.BackQueryDto;
import com.hfy.dinner.repository.pojo.Back;
import com.hfy.dinner.repository.pojo.ResponseDo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author hfy
 * @date 2021/5/9 20:10
 */
@Service
public class BackService {

    @Resource
    private BackDao backDao;
    @Autowired
    private JavaMailSender mailSender;

    public PageInfo<?> query(BackQueryDto queryDto) {
        int page = queryDto.getOffset() / queryDto.getLimit() + 1;
        PageHelper.startPage(page, queryDto.getLimit());
        QueryWrapper<Back> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(queryDto.getName())){
            query.like("user_name",queryDto.getName());
        }
        if(queryDto.getType()!=null){
            query.eq("type",queryDto.getType());
        }

        List<Back> backs = backDao.selectList(query);
        return new PageInfo<>(backs);
    }

    public ResponseDo update(Back back) {
        boolean isSuccess = simpleMailSend(back.getEmail(), "共享晚餐工单回复", back.getBackContent());
        if(!isSuccess){
            return new ResponseDo(400,"邮件发送失败");
        }
        back.setBackTime(new Date());
        back.setType(1);
        backDao.updateById(back);
        return new ResponseDo(200,"回复成功");
    }

    public  boolean simpleMailSend(String email,String subject,String text) {
        //创建邮件内容
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("18715851323@163.com");//这里指的是发送者的账号
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        //发送邮件
        try {
            mailSender.send(message);
            System.out.println("\033[32;1m" + "发送给 " + email + " 的邮件发送成功" + "\033[0m");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
