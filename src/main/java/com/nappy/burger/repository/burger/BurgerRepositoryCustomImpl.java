package com.nappy.burger.repository.burger;

import com.nappy.burger.domain.burger.Burger;
import com.nappy.burger.domain.burger.BurgerSellStatus;
import com.nappy.burger.domain.burger.QBurger;
import com.nappy.burger.domain.burger.QBurgerImg;
import com.nappy.burger.dto.burger.BurgerSearchDto;
import com.nappy.burger.dto.burger.MainBurgerDto;
import com.nappy.burger.dto.burger.QMainBurgerDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class BurgerRepositoryCustomImpl implements BurgerRepositoryCustom {

    private JPAQueryFactory queryFactory;

    // 생성자 DI를 통해서 JPAQueryFactory(EntityManager) 주입
    public BurgerRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    // 상품 등록일에 대한 조회 조건 BooleanExpression
    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();

        if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }

        return QBurger.burger.regTime.after(dateTime);
    }

    // 상품 상태에 대한 조회 조건 BooleanExpression
    private BooleanExpression searchSellStatusEq(BurgerSellStatus searchSellStatus) {
        return searchSellStatus == null ? null : QBurger.burger.burgerSellStatus.eq(searchSellStatus);
    }

    // 상품명 또는 등록자 아이디에 대한 조회 조건 BooleanExpression
    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if (StringUtils.equals("burgerName", searchBy)) {
            return QBurger.burger.burgerName.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("createdBy", searchBy)) {
            return QBurger.burger.createdBy.like("%" + searchQuery + "%");
        }
        return null;
    }

    // 이름으로 검색
    private BooleanExpression burgerNameLike(String searchQuery) {
        return StringUtils.isEmpty(searchQuery) ? null : QBurger.burger.burgerName.like("%" + searchQuery + "%");
    }

    @Override
    public Page<Burger> getAdminBurgerPage(BurgerSearchDto burgerSearchDto, Pageable pageable) {

        // queryFactory 를 이용하여 쿼리문 생성
        QueryResults<Burger> results = queryFactory
                .selectFrom(QBurger.burger)
                .where(regDtsAfter(burgerSearchDto.getSearchDateType()),
                        searchSellStatusEq(burgerSearchDto.getSearchSellStatus()),
                        searchByLike(burgerSearchDto.getSearchBy(), burgerSearchDto.getSearchQuery()))
                .orderBy(QBurger.burger.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();    // 2번의 select 문 실행

        // 조회 대상 리스트 결과
        List<Burger> content = results.getResults();

        // 조회 대상 리스트의 개수 (count)
        long total = results.getTotal();

        // Page 인터페이스를 구현한 PageImpl 객체 반환
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<MainBurgerDto> getMainBurgerPage(BurgerSearchDto burgerSearchDto, Pageable pageable) {
        QBurger burger = QBurger.burger;
        QBurgerImg burgerImg = QBurgerImg.burgerImg;

        QueryResults<MainBurgerDto> result = queryFactory
                .select(
                        new QMainBurgerDto(
                                burger.id,
                                burger.burgerName,
                                burger.burgerExplanation,
                                burgerImg.imgUrl,
                                burger.price)
                )
                .from(burgerImg)
                .join(burgerImg.burger, burger)
                .where(burgerImg.repimgYn.eq("Y"))
                .where(burgerNameLike(burgerSearchDto.getSearchQuery()))
                .orderBy(burger.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MainBurgerDto> content = result.getResults();
        long total = result.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
