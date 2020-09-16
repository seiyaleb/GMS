package com.seiya.springboot.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.seiya.springboot.Account;
import com.seiya.springboot.member;

@Repository
public interface memberRepository extends JpaRepository<member,Long> {

	public List<member> findByAccount(Account account);
	public Optional<member> findByMemberid(Long memberid);
	public List<member> findByMembernameLikeAndAccount(String membername, Account account);
	public List<member> findByGenderAndAccountOrderByGradeDesc(String gender, Account account);
	public List<member> findByPositionLikeAndAccountOrderByMemberidAsc(String position, Account account);
	public List<member> findByGradeAndAccountOrderByMembernameAsc(int grade, Account account);
	public List<member> findByPayFlagAndAccountOrderByGradeDesc(int payFlag, Account account);
	
	@Query("select m from member m where m.grade = ?1 and m.gender = ?2 and m.payFlag = ?3 and m.account = ?4")
	List<member> selectGradeAndGenderAndPay_flag(int grade, String gender, int payFlag, Account account);
	
	@Query("select m from member m where m.grade >= ?1 and m.account = ?2 order by m.grade asc")
	List<member> selectGradeUp(int grade, Account account);
	
	@Query("select m from member m where m.grade <= ?1 and m.account = ?2 order by m.grade desc")
	List<member> selectGradeDown(int grade, Account account);
	
	@Query("select m from member m where m.grade between ?1 and ?2 and m.account = ?3 order by m.grade asc")
	List<member> selectGradeBetween(int grade1, int grade2, Account account);
}
