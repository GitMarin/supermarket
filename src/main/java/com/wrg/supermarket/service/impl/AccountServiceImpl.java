package com.wrg.supermarket.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrg.supermarket.entity.Account;
import com.wrg.supermarket.mapper.AccountMapper;
import com.wrg.supermarket.service.IAccountService;
import org.springframework.stereotype.Service;

/**
 * @ClassName AccountServiceImpl
 * @Description TODO
 * @Author Wang Rengang
 * @Date 2019/5/8 22:10
 * @Version 1.0
 **/
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {
}
