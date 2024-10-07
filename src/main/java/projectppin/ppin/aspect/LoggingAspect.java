package projectppin.ppin.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import projectppin.ppin.DTO.DataLogDTO;
import projectppin.ppin.Repository.CompanyRepository;
import projectppin.ppin.Service.DataLogService;
import projectppin.ppin.domain.CompanyList;

import java.util.Optional;

@Aspect
@Component
public class LoggingAspect {

    @Autowired
    private DataLogService dataLogService;

    @Autowired
    private CompanyRepository companyRepository;

//     회사 정보가 수정될 때 로그 기록
//    @AfterReturning(pointcut = "execution(* projectppin.ppin.Service.CompanyService.updateCompany(..))", returning = "result")
//    public void logCompanyUpdate(JoinPoint joinPoint, Object result) {
//        Object[] args = joinPoint.getArgs();
//        Long cnb = (Long) args[0];  // 회사 ID
//
//        // 수정 전의 데이터를 데이터베이스에서 다시 가져오기
//        Optional<CompanyList> companyOpt = companyRepository.findById(cnb);
//        if (!companyOpt.isPresent()) {
//            return; // 회사 정보를 찾을 수 없는 경우 처리 중단
//        }
//        CompanyList oldCompany = companyOpt.get(); // 기존 정보
//
//        String oldPosition = oldCompany.getPosition();
//        String oldDepartment = oldCompany.getDepartment();
//        Long oldBaseSalary = oldCompany.getBaseSalary();
//
//        // 새로운 데이터는 JoinPoint에서 가져옴
//        String newPosition = (String) args[1];  // 새 직책
//        String newDepartment = (String) args[2];  // 새 부서
//        Long newBaseSalary = (Long) args[3];  // 새 기준 봉급
//
//        // 사용자 정보는 SecurityContext에서 가져오기
//        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        // 로그 기록
//        String oldData = "기존 정보 - 직책: " + oldPosition + ", 부서: " + oldDepartment + ", 기준 봉급: " + oldBaseSalary;
//        String newData = "새로운 정보 - 직책: " + newPosition + ", 부서: " + newDepartment + ", 기준 봉급: " + newBaseSalary;
//
//        DataLogDTO logDTO = new DataLogDTO();
//        logDTO.setActionType("회사 수정");
//        logDTO.setEntityType("CompanyList");
//        logDTO.setEntityId(cnb.intValue());
//        logDTO.setOldData(oldData);
//        logDTO.setNewData(newData);
//        logDTO.setComments("회사 정보가 수정되었습니다.");
//
//        dataLogService.logAction(logDTO, userId);
//    }

//    @AfterReturning(pointcut = "execution(* projectppin.ppin.Service.CompanyService.updateCompany(..))", returning = "result")
//    public void logCompanyUpdate(JoinPoint joinPoint, Object result) {
//        Object[] args = joinPoint.getArgs();
//        Long cnb = (Long) args[0];  // 회사 ID
//
//        // 수정 전의 데이터를 데이터베이스에서 다시 가져오기
//        Optional<CompanyList> companyOpt = companyRepository.findById(cnb);
//        if (!companyOpt.isPresent()) {
//            return; // 회사 정보를 찾을 수 없는 경우 처리 중단
//        }
//        CompanyList oldCompany = companyOpt.get(); // 기존 정보
//
//        // 기존 데이터 저장 (변경되기 전에 따로 저장해둠)
//        String oldPosition = oldCompany.getPosition();
//        String oldDepartment = oldCompany.getDepartment();
//        Long oldBaseSalary = oldCompany.getBaseSalary();
//
//        // 새로운 데이터는 JoinPoint에서 가져옴
//        String newPosition = (String) args[1];  // 새 직책
//        String newDepartment = (String) args[2];  // 새 부서
//        Long newBaseSalary = (Long) args[3];  // 새 기준 봉급
//
//        // 사용자 정보는 SecurityContext에서 가져오기
//        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        // 로그 기록 - 변경 전의 값(oldData)과 변경 후의 값(newData)을 각각 기록
//        String oldData = "기존 정보 - 직책: " + oldPosition + ", 부서: " + oldDepartment + ", 기준 봉급: " + oldBaseSalary;
//        String newData = "새로운 정보 - 직책: " + newPosition + ", 부서: " + newDepartment + ", 기준 봉급: " + newBaseSalary;
//
//        DataLogDTO logDTO = new DataLogDTO();
//        logDTO.setActionType("회사 수정");
//        logDTO.setEntityType("CompanyList");
//        logDTO.setEntityId(cnb.intValue());
//        logDTO.setOldData(oldData);  // 수정 전 정보
//        logDTO.setNewData(newData);  // 수정 후 정보
//        logDTO.setComments("회사 정보가 수정되었습니다.");
//
//        dataLogService.logAction(logDTO, userId);
//    }
}
