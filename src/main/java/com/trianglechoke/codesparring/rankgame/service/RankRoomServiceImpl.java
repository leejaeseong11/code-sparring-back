package com.trianglechoke.codesparring.rankgame.service;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.member.dao.MemberRepository;
import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.quiz.dao.QuizRepository;
import com.trianglechoke.codesparring.quiz.entity.Quiz;
import com.trianglechoke.codesparring.rankgame.dao.RankGameRepository;
import com.trianglechoke.codesparring.rankgame.dao.RankRoomRepository;
import com.trianglechoke.codesparring.rankgame.dto.RankGameDTO;
import com.trianglechoke.codesparring.rankgame.dto.RankRoomDTO;
import com.trianglechoke.codesparring.rankgame.entity.RankGame;

import com.trianglechoke.codesparring.rankgame.entity.RankRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class RankRoomServiceImpl implements RankRoomService {
    @Autowired private RankRoomRepository repository;
    @Autowired private RankGameRepository gameRepository;
    @Autowired private MemberRepository memberRepository;

    /* SELECT 랭크 번호 */
    public RankRoomDTO findByRoomNo(Long roomNo) throws MyException {
        Optional<RankRoom> optRankRoom = repository.findById(roomNo);
        RankRoom rankRoom = optRankRoom.get();
        RankRoomDTO rankRoomDTO =
                RankRoomDTO.builder()
                        .roomNo(roomNo)
                        .member1No(rankRoom.getMember1().getMemberNo())
                        .member2No(rankRoom.getMember2No())
                        .readyCnt(rankRoom.getReadyCnt())
                        .quizNo(rankRoom.getQuizNo())
                        .build();
        return rankRoomDTO;
    }

    /* SELECT 티어 */
    public RankRoom findByRankTier(String tier) throws MyException {
        RankRoom exampleRankRoom = RankRoom.builder().tier(tier).readyCnt(0).build();
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll();
        Example<RankRoom> example = Example.of(exampleRankRoom, exampleMatcher);
        Sort sort = Sort.by(Sort.Order.asc("regdate"));
        List<RankRoom> rankRoomList = repository.findAll(example, sort);
        if (rankRoomList.size() == 0) return null;
        else return rankRoomList.get(0);
    }

    /* INSERT */
    public Long saveRankGame(Long memberNo) throws MyException {
        Optional<Member> optMember = memberRepository.findById(memberNo);
        Member member = optMember.get();
        log.error("{}", member);
        RankRoom rankRoom = RankRoom.builder().member1(member).tier(member.getMemberTier()).build();
        repository.save(rankRoom);
        return rankRoom.getRankRoomNo();
    }

    /* UPDATE 멤버2 */
    public void matchingMember(Long roomNo, Long memberNo) throws MyException {
        Optional<RankRoom> optRankRoom = repository.findById(roomNo);
        RankRoom rankRoom = optRankRoom.get();
        rankRoom.addMember2(memberNo);
        rankRoom.ready();
        repository.save(rankRoom);
    }

    /* UPDATE 준비 */
    public void memberReady(Long roomNo) throws MyException {
        Optional<RankRoom> optRankRoom = repository.findById(roomNo);
        RankRoom rankRoom = optRankRoom.get();
        rankRoom.ready();
        repository.save(rankRoom);
    }

    /* UPDATE 문제 */
    public void modifyGameQuiz(Long roomNo) throws MyException {
        Optional<RankRoom> optRankRoom = repository.findById(roomNo);
        RankRoom entity = optRankRoom.get();
        String tier = entity.getMember1().getMemberTier();
        Long quizNo = matchingRandomQuiz(tier);
        entity.modifyGameQuiz(quizNo);
        repository.save(entity);
        Optional<Member> optMember = memberRepository.findById(entity.getMember2No());
        Member member2 = optMember.get();

        RankGame gameEntity=RankGame.builder()
                .member1(entity.getMember1())
                .member2(member2)
                .quizNo(quizNo).roomNo(roomNo).build();
        gameRepository.save(gameEntity);
    }

    /* DELETE */
    public void removeMatching(Long rankNo) throws MyException {
        repository.deleteById(rankNo);
    }

    @Autowired private QuizRepository quizRepository;

    /* 문제 랜덤 매칭 */
    private Long matchingRandomQuiz(String quizTier) throws MyException {
        Quiz exampleQuiz = Quiz.builder().quizTier(quizTier).build();
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll();
        Example<Quiz> example = Example.of(exampleQuiz, exampleMatcher);
        List<Quiz> quizList = quizRepository.findAll(example);

        Random random = new Random();
        int size = quizList.size();
        int index = random.nextInt(size);
        Long quizNo = quizList.get(index).getQuizNo();

        return quizNo;
    }
}
