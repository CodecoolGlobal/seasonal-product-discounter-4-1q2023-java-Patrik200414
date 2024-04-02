package com.codecool.seasonalproductdiscounter.service.discounts;

import com.codecool.seasonalproductdiscounter.model.discounts.ColorDiscount;
import com.codecool.seasonalproductdiscounter.model.discounts.Discount;
import com.codecool.seasonalproductdiscounter.model.discounts.MonthlyDiscount;
import com.codecool.seasonalproductdiscounter.model.enums.Color;
import com.codecool.seasonalproductdiscounter.model.enums.Season;
import com.codecool.seasonalproductdiscounter.model.offers.Offer;
import com.codecool.seasonalproductdiscounter.model.products.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

class DiscountServiceImplTest {

    DiscountProvider discountProviderMock;

    @BeforeEach
    void setUp(){
        discountProviderMock = mock(DiscountProvider.class);
    }
    @Test
    void getOffer_DiscountProviderMockReturnsSummerKickOffSale_ExpectedProductAppliesToDiscount_ShouldReturnExpectedOffer() {
        Discount expectedDiscount = new MonthlyDiscount("Summer Kick-off", 10, Set.of(Month.JUNE, Month.JULY));
        when(discountProviderMock.getDiscounts()).thenReturn(List.of(
                expectedDiscount
        ));
        Product expectedProduct = new Product(
                1,
                "Red T-shirt",
                Color.RED,
                Season.SUMMER,
                10,
                false
        );
        LocalDate expectedDate = LocalDate.of(2024,7,12);

        DiscountService discountService = new DiscountServiceImpl(discountProviderMock);


        Offer result = discountService.getOffer(expectedProduct, expectedDate);
        Offer expected = new Offer(
                expectedProduct,
                expectedDate,
                List.of(expectedDiscount),
                9
        );

        assertEquals(expected, result);
    }


    @Test
    void getOffer_DiscountProviderMockReturnsSummerKickOffSaleAndYellowSummer_ExpectedProductAppliesToBothDiscounts_ShouldReturnExpectedOffer() {
        Discount firstExpectedDiscount = new MonthlyDiscount("Summer Kick-off", 10, Set.of(Month.JUNE, Month.JULY));
        Discount secondExpectedDiscount = new ColorDiscount("Yellow Summer", 5, Color.YELLOW, Season.SUMMER);
        when(discountProviderMock.getDiscounts()).thenReturn(List.of(
                firstExpectedDiscount,
                secondExpectedDiscount
        ));
        Product expectedProduct = new Product(
                1,
                "Yellow T-shirt",
                Color.YELLOW,
                Season.SUMMER,
                10,
                false
        );
        LocalDate expectedDate = LocalDate.of(2024,7,12);

        DiscountService discountService = new DiscountServiceImpl(discountProviderMock);


        Offer result = discountService.getOffer(expectedProduct, expectedDate);
        Offer expected = new Offer(
                expectedProduct,
                expectedDate,
                List.of(
                        firstExpectedDiscount,
                        secondExpectedDiscount
                ),
                8.5
        );

        assertEquals(expected, result);
    }


    @Test
    void getOffer_DiscountProviderMockReturnsSummerKickOffSaleAndYellowSummer_ExpectedProductAppliesToSummerKickOffSaleDiscount_ShouldReturnExpectedOffer() {
        Discount firstExpectedDiscount = new MonthlyDiscount("Summer Kick-off", 10, Set.of(Month.JUNE, Month.JULY));
        Discount secondExpectedDiscount = new ColorDiscount("Yellow Summer", 5, Color.YELLOW, Season.SUMMER);
        when(discountProviderMock.getDiscounts()).thenReturn(List.of(
                firstExpectedDiscount,
                secondExpectedDiscount
        ));
        Product expectedProduct = new Product(
                1,
                "Brown T-shirt",
                Color.BROWN,
                Season.SUMMER,
                10,
                false
        );
        LocalDate expectedDate = LocalDate.of(2024,7,12);

        DiscountService discountService = new DiscountServiceImpl(discountProviderMock);


        Offer result = discountService.getOffer(expectedProduct, expectedDate);
        Offer expected = new Offer(
                expectedProduct,
                expectedDate,
                List.of(
                        firstExpectedDiscount
                ),
                9
        );

        assertEquals(expected, result);
    }

    @Test
    void getOffer_DiscountProviderMockReturnsSummerKickOffSaleAndYellowSummer_ExpectedProductNotAppliesToAnyDiscount_ShouldReturnExpectedOffer() {
        Discount firstExpectedDiscount = new MonthlyDiscount("Summer Kick-off", 10, Set.of(Month.JUNE, Month.JULY));
        Discount secondExpectedDiscount = new ColorDiscount("Yellow Summer", 5, Color.YELLOW, Season.SUMMER);
        when(discountProviderMock.getDiscounts()).thenReturn(List.of(
                firstExpectedDiscount,
                secondExpectedDiscount
        ));
        Product expectedProduct = new Product(
                1,
                "Brown T-shirt",
                Color.BROWN,
                Season.WINTER,
                10,
                false
        );
        LocalDate expectedDate = LocalDate.of(2024,12,12);

        DiscountService discountService = new DiscountServiceImpl(discountProviderMock);


        Offer result = discountService.getOffer(expectedProduct, expectedDate);
        Offer expected = new Offer(
                expectedProduct,
                expectedDate,
                List.of(),
                10
        );

        assertEquals(expected, result);
    }
}