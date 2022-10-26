package com.nappy.burger.repository.order;

import com.nappy.burger.domain.burger.QBurger;
import com.nappy.burger.domain.burger.QBurgerImg;
import com.nappy.burger.domain.order.QOrder;
import com.nappy.burger.domain.order.QOrderBurger;
import com.nappy.burger.dto.order.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class OrderBurgerRepositoryCustomImpl implements OrderBurgerRepositoryCustom {

    private JPAQueryFactory jpaQueryFactory;

    // 생성자 DI를 통해서 JPAQueryFactory(EntityManager) 주입
    public OrderBurgerRepositoryCustomImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
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

        return QOrderBurger.orderBurger.regTime.after(dateTime);
    }


    @Override
    public List<OrderBurgerHistDto> getAdminTotalPage(OrderBurgerSearchDto orderBurgerSearchDto) {
        QOrder order = QOrder.order;
        QOrderBurger orderBurger = QOrderBurger.orderBurger;
        QBurgerImg burgerImg = QBurgerImg.burgerImg;
        QBurger burger = QBurger.burger;

        QueryResults<OrderBurgerHistDto> results = jpaQueryFactory
                .select(new QOrderBurgerHistDto(
                        orderBurger.burger.id
                        , orderBurger.count.sum()
                        , burgerImg.imgUrl
                        , burger.price
                        , orderBurger.burger.burgerName))
                .from(orderBurger, burgerImg)
                .join(burgerImg.burger, burger)
                .where(burgerImg.repimgYn.eq("Y"))
                .where(regDtsAfter(orderBurgerSearchDto.getSearchDateType()))
                .groupBy(orderBurger.burger.id)
                .orderBy(orderBurger.count.sum().desc())
                .limit(5)
                .fetchResults();

        List<OrderBurgerHistDto> content = results.getResults();

        return content;
    }

    @Override
    public List<OrderBurgerTypeHistDto> getAdminTypePage(OrderBurgerSearchDto orderBurgerSearchDto){
        QOrderBurger orderBurger = QOrderBurger.orderBurger;
        QBurgerImg burgerImg = QBurgerImg.burgerImg;
        QBurger burger = QBurger.burger;

        QueryResults<OrderBurgerTypeHistDto> results = jpaQueryFactory
                .select(new QOrderBurgerTypeHistDto(
                        orderBurger.burger.burgerType,
                        orderBurger.count.sum()))
                .from(orderBurger)
                .where(regDtsAfter(orderBurgerSearchDto.getSearchDateType()))
                .groupBy(orderBurger.burger.burgerType)
                .orderBy(orderBurger.count.sum().desc())
                .limit(5)
                .fetchResults();

        List<OrderBurgerTypeHistDto> content = results.getResults();

        return content;
    }

}
