package com.trianglechoke.codesparring.rankgame.service;

import com.trianglechoke.codesparring.exception.MyException;
import com.trianglechoke.codesparring.member.dao.MemberRepository;
import com.trianglechoke.codesparring.member.entity.Member;
import com.trianglechoke.codesparring.quiz.dao.QuizRepository;
import com.trianglechoke.codesparring.quiz.entity.Quiz;
import com.trianglechoke.codesparring.rankgame.dao.RankGameRepository;
import com.trianglechoke.codesparring.rankgame.dto.RankGameDTO;
import com.trianglechoke.codesparring.rankgame.entity.RankGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class RankMatchServiceImpl implements RankMatchService {
    @Autowired private RankGameRepository repository;
    @Autowired private MemberRepository memberRepository;

    /* SELECT 랭크 번호 */
    public RankGameDTO findByRankNo(Long rankNo) throws MyException {
        Optional<RankGame> optRankGame = repository.findById(rankNo);
        RankGame rankGame = optRankGame.get();
        RankGameDTO rankGameDTO =
                RankGameDTO.builder()
                        .rankNo(rankNo)
                        .member1No(rankGame.getMember1().getMemberNo())
                        .member1Name(rankGame.getMember1().getMemberName())
                        .member2No(rankGame.getMember2().getMemberNo())
                        .member2Name(rankGame.getMember2().getMemberName())
                        .readyCnt(rankGame.getReadyCnt())
                        .build();
        return rankGameDTO;
    }

    /* SELECT 티어 */
    public Long findByRankTier(String tier) throws MyException {
        RankGame exampleRankGame = RankGame.builder()
                .tier(tier)
                .member2(null)
                .build();
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll();
        Example<RankGame> example = Example.of(exampleRankGame, exampleMatcher);
        Sort sort = Sort.by(Sort.Order.asc("regdate"));
        List<RankGame> rankGameList=repository.findAll(example, sort);
        if(rankGameList.size()==0) return 0L;
        else return rankGameList.get(0).getRankNo();
    }

    /* INSERT */
    public void saveRankGame(Long memberNo) throws MyException {
        Optional<Member> optMember=memberRepository.findById(memberNo);
        Member member=optMember.get();
        RankGame rankGame= RankGame.builder()
                .member1(member)
                .tier(member.getMemberTier()).build();
        repository.save(rankGame);
    }

    /* UPDATE 멤버2 */
    public void matchingMember(Long rankNo, Long memberNo) throws MyException {
        Optional<Member> optMember=memberRepository.findById(memberNo);
        Member member=optMember.get();
        Optional<RankGame> optRankGame=repository.findById(rankNo);
        RankGame rankGame=optRankGame.get();
        rankGame.addMember2(member);
        repository.save(rankGame);
    }

    /* UPDATE 준비 */
    public void memberReady(Long rankNo) throws MyException {
        Optional<RankGame> optRankGame=repository.findById(rankNo);
        RankGame rankGame=optRankGame.get();
        rankGame.ready();
        repository.save(rankGame);
    }

    /* UPDATE 문제 */
    public Long modifyGameQuiz(Long rankNo) throws MyException {
        Optional<RankGame> optRg = repository.findById(rankNo);
        RankGame entity = optRg.get();
        String tier = entity.getMember1().getMemberTier();
        Long quizNo = matchingRandomQuiz(tier);
        entity.modifyGameQuiz(quizNo);
        repository.save(entity);
        return quizNo;
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
