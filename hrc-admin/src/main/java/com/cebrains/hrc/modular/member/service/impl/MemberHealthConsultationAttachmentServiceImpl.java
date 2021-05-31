package com.cebrains.hrc.modular.member.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cebrains.hrc.common.persistence.dao.MemberHealthConsultationAttachmentMapper;
import com.cebrains.hrc.common.persistence.model.MemberHealthConsultationAttachment;
import com.cebrains.hrc.modular.member.service.IMemberHealthConsultationAttachmentService;
import org.springframework.stereotype.Service;

/**
 * 健康咨询附件 服务实现类
 */
@Service
public class MemberHealthConsultationAttachmentServiceImpl extends ServiceImpl<MemberHealthConsultationAttachmentMapper, MemberHealthConsultationAttachment> implements IMemberHealthConsultationAttachmentService {
}
