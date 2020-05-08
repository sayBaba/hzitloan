package com.hzit.repay.mapper;

import com.hzit.repay.model.Repayment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RepaymentMapper {

    /**
     * 创建流水
     * @param repayment
     * @return
     */
    public int insert(Repayment repayment);

    /**
     * 修改流水
     * @param repayment
     * @return
     */
    public int update(Repayment repayment);


}
