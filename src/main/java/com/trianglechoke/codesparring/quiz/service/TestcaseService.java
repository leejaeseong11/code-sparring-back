package com.trianglechoke.codesparring.quiz.service;

import com.trianglechoke.codesparring.quiz.Repository.TestcaseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestcaseService {
    @Autowired private TestcaseRepository repository;

    /* quizNo에 해당하는 testcase 목록 조회 */
    //    public List<TestcaseDTO> findAllByQuizNo(Long quizNo) throws FindException {
    //        List<TestcaseDTO> tcDTOList = new ArrayList<>();
    //        List<Object[]> list = repository.findAllByQuizNo(quizNo);
    //        for (Object[] objArr : list) {
    //            try {
    //                TestcaseDTO dto =
    //                        TestcaseDTO.builder()
    //                                .testcaseNo(Long.valueOf(String.valueOf(objArr[0])))
    //                                .quizNo(Long.valueOf(String.valueOf(objArr[1])))
    //                                .build();
    //
    //                if (objArr[3] != null) dto.setTestcaseOutput(String.valueOf(objArr[3]));
    //                tcDTOList.add(dto);
    //            } catch (Exception e) {
    //            }
    //        }
    //        return tcDTOList;
    //    }

    /* testcase 추가 */
    //    public void addTestcase(TestcaseDTO tcDTO) throws AddException {
    //        Testcase tcEntity =
    //                Testcase.builder()
    //                        .quizNo(tcDTO.getQuizNo())
    //                        .testcaseOutput(tcDTO.getTestcaseOutput())
    //                        .build();
    //        repository.save(tcEntity);
    //    }

    /* testcase 수정 */
    //    public void modifyTestcase(TestcaseDTO tcDTO) throws ModifyException {
    //        Optional<Testcase> optTc = repository.findById(tcDTO.getTestcaseNo());
    //        Testcase tcEntity = optTc.get();
    //        repository.save(tcEntity);
    //    }

    /* testcase 삭제 */
    //    public void removeTestcase(Long testcaseNo) throws RemoveException {
    //        repository.deleteById(testcaseNo);
    //    }
}
