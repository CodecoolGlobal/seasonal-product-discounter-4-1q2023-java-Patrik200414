package com.codecool.seasonalproductdiscounter.service.products.browser;

import com.codecool.seasonalproductdiscounter.model.enums.Color;
import com.codecool.seasonalproductdiscounter.model.enums.Season;
import com.codecool.seasonalproductdiscounter.model.products.PriceRange;
import com.codecool.seasonalproductdiscounter.model.products.Product;
import com.codecool.seasonalproductdiscounter.service.products.provider.ProductProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

class ProductBrowserImplTest {

    ProductProvider productProviderMock;

    @BeforeEach
    void setUp(){
        productProviderMock = mock(ProductProvider.class);
    }

    @Test
    void getAll_ShouldReturnExpectedProducts() {
        List<Product> expectedProducts = List.of(
                new Product(1, "Yellow shirt", Color.YELLOW, Season.SUMMER, 10, false),
                new Product(2, "Red shirt", Color.RED, Season.SPRING, 10, true),
                new Product(3, "Blue shirt", Color.BLUE, Season.AUTUMN, 10, false),
                new Product(4, "Green shirt", Color.GREEN, Season.SPRING, 10, false),
                new Product(5, "Yellow shirt", Color.YELLOW, Season.SPRING, 10, false)
        );
        when(productProviderMock.getProducts()).thenReturn(
                expectedProducts
        );

        ProductBrowser productBrowser = new ProductBrowserImpl(productProviderMock);
        List<Product> result = productBrowser.getAll();

        assertArrayEquals(expectedProducts.toArray(), result.toArray());
    }

    @Test
    void getByName_ShouldReturnListWithTwoProducts_BothNameShouldBeYellowShirt() {
        List<Product> expectedProducts = List.of(
                new Product(1, "Yellow shirt", Color.YELLOW, Season.SUMMER, 10, false),
                new Product(2, "Red shirt", Color.RED, Season.SPRING, 10, true),
                new Product(3, "Blue shirt", Color.BLUE, Season.AUTUMN, 10, false),
                new Product(4, "Green shirt", Color.GREEN, Season.SPRING, 10, false),
                new Product(5, "Yellow shirt", Color.YELLOW, Season.SPRING, 10, false)
        );
        when(productProviderMock.getProducts()).thenReturn(
                expectedProducts
        );

        ProductBrowser productBrowser = new ProductBrowserImpl(productProviderMock);
        List<Product> result = productBrowser.getByName("Yellow shirt");
        List<Product> expected = List.of(
                new Product(1, "Yellow shirt", Color.YELLOW, Season.SUMMER, 10, false),
                new Product(5, "Yellow shirt", Color.YELLOW, Season.SPRING, 10, false)
        );

        assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test
    void getByColor_ShouldReturnListWithTwoProducts_BothColorShouldBeYellow() {
        List<Product> expectedProducts = List.of(
                new Product(1, "Yellow shirt", Color.YELLOW, Season.SUMMER, 10, false),
                new Product(2, "Red shirt", Color.RED, Season.SPRING, 10, true),
                new Product(3, "Blue shirt", Color.BLUE, Season.AUTUMN, 10, false),
                new Product(4, "Green shirt", Color.GREEN, Season.SPRING, 10, false),
                new Product(5, "Yellow shirt", Color.YELLOW, Season.SPRING, 10, false)
        );
        when(productProviderMock.getProducts()).thenReturn(
                expectedProducts
        );

        ProductBrowser productBrowser = new ProductBrowserImpl(productProviderMock);

        List<Product> result = productBrowser.getByColor(Color.YELLOW);
        List<Product> expected = List.of(
                new Product(1, "Yellow shirt", Color.YELLOW, Season.SUMMER, 10, false),
                new Product(5, "Yellow shirt", Color.YELLOW, Season.SPRING, 10, false)
        );

        assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test
    void getBySeason_ShouldReturnListWithThreeProducts_AllProductsShouldHaveSpringSeason() {
        List<Product> expectedProducts = List.of(
                new Product(1, "Yellow shirt", Color.YELLOW, Season.SUMMER, 10, false),
                new Product(2, "Red shirt", Color.RED, Season.SPRING, 10, true),
                new Product(3, "Blue shirt", Color.BLUE, Season.AUTUMN, 10, false),
                new Product(4, "Green shirt", Color.GREEN, Season.SPRING, 10, false),
                new Product(5, "Yellow shirt", Color.YELLOW, Season.SPRING, 10, false)
        );
        when(productProviderMock.getProducts()).thenReturn(
                expectedProducts
        );

        ProductBrowser productBrowser = new ProductBrowserImpl(productProviderMock);

        List<Product> result = productBrowser.getBySeason(Season.SPRING);
        List<Product> expected = List.of(
                new Product(2, "Red shirt", Color.RED, Season.SPRING, 10, true),
                new Product(4, "Green shirt", Color.GREEN, Season.SPRING, 10, false),
                new Product(5, "Yellow shirt", Color.YELLOW, Season.SPRING, 10, false)
        );

        assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test
    void getByPriceSmallerThan_ShouldReturnListWithTwoProducts_BothProductsShouldHavePriceLowerThan6() {
        List<Product> expectedProducts = List.of(
                new Product(1, "Yellow shirt", Color.YELLOW, Season.SUMMER, 7, false),
                new Product(2, "Red shirt", Color.RED, Season.SPRING, 10, true),
                new Product(3, "Blue shirt", Color.BLUE, Season.AUTUMN, 5, false),
                new Product(4, "Green shirt", Color.GREEN, Season.SPRING, 11, false),
                new Product(5, "Yellow shirt", Color.YELLOW, Season.SPRING, 3, false)
        );
        when(productProviderMock.getProducts()).thenReturn(
                expectedProducts
        );

        ProductBrowser productBrowser = new ProductBrowserImpl(productProviderMock);

        List<Product> result = productBrowser.getByPriceSmallerThan(6);
        List<Product> expected = List.of(
                new Product(3, "Blue shirt", Color.BLUE, Season.AUTUMN, 5, false),
                new Product(5, "Yellow shirt", Color.YELLOW, Season.SPRING, 3, false)
        );

        assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test
    void getByPriceGreaterThan_ShouldReturnListWithThreeProducts_AllProductsShouldHavePriceGreaterThan6() {
        List<Product> expectedProducts = List.of(
                new Product(1, "Yellow shirt", Color.YELLOW, Season.SUMMER, 7, false),
                new Product(2, "Red shirt", Color.RED, Season.SPRING, 10, true),
                new Product(3, "Blue shirt", Color.BLUE, Season.AUTUMN, 5, false),
                new Product(4, "Green shirt", Color.GREEN, Season.SPRING, 11, false),
                new Product(5, "Yellow shirt", Color.YELLOW, Season.SPRING, 3, false)
        );
        when(productProviderMock.getProducts()).thenReturn(
                expectedProducts
        );

        ProductBrowser productBrowser = new ProductBrowserImpl(productProviderMock);

        List<Product> result = productBrowser.getByPriceGreaterThan(6);
        List<Product> expected = List.of(
                new Product(1, "Yellow shirt", Color.YELLOW, Season.SUMMER, 7, false),
                new Product(2, "Red shirt", Color.RED, Season.SPRING, 10, true),
                new Product(4, "Green shirt", Color.GREEN, Season.SPRING, 11, false)
        );

        assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test
    void getByPriceRange_ShouldReturnListWithThreeProducts_AllProductsPriceShouldBeBetween4And11() {
        List<Product> expectedProducts = List.of(
                new Product(1, "Yellow shirt", Color.YELLOW, Season.SUMMER, 7, false),
                new Product(2, "Red shirt", Color.RED, Season.SPRING, 10, true),
                new Product(3, "Blue shirt", Color.BLUE, Season.AUTUMN, 5, false),
                new Product(4, "Green shirt", Color.GREEN, Season.SPRING, 11, false),
                new Product(5, "Yellow shirt", Color.YELLOW, Season.SPRING, 3, false)
        );
        when(productProviderMock.getProducts()).thenReturn(
                expectedProducts
        );

        ProductBrowser productBrowser = new ProductBrowserImpl(productProviderMock);

        List<Product> result = productBrowser.getByPriceRange(4, 11);
        List<Product> expected = List.of(
                new Product(1, "Yellow shirt", Color.YELLOW, Season.SUMMER, 7, false),
                new Product(2, "Red shirt", Color.RED, Season.SPRING, 10, true),
                new Product(3, "Blue shirt", Color.BLUE, Season.AUTUMN, 5, false)
        );

        assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test
    void groupByName_ShouldReturnAMapWithProductNameAsKeyAndListOfProductsAsValue_ShouldReturnExpectedMap() {
        List<Product> expectedProducts = List.of(
                new Product(1, "Yellow shirt", Color.YELLOW, Season.SUMMER, 7, false),
                new Product(2, "Red shirt", Color.RED, Season.SPRING, 10, true),
                new Product(3, "Blue shirt", Color.BLUE, Season.AUTUMN, 5, false),
                new Product(4, "Green shirt", Color.GREEN, Season.SPRING, 11, false),
                new Product(5, "Yellow shirt", Color.YELLOW, Season.SPRING, 3, false)
        );
        when(productProviderMock.getProducts()).thenReturn(
                expectedProducts
        );

        ProductBrowser productBrowser = new ProductBrowserImpl(productProviderMock);

        Map<String, List<Product>> expected = new HashMap<>();
        expected.put("Yellow shirt", List.of(
                new Product(1, "Yellow shirt", Color.YELLOW, Season.SUMMER, 7, false),
                new Product(5, "Yellow shirt", Color.YELLOW, Season.SPRING, 3, false)
        ));
        expected.put("Red shirt", List.of(
                new Product(2, "Red shirt", Color.RED, Season.SPRING, 10, true)
        ));
        expected.put("Blue shirt", List.of(
                new Product(3, "Blue shirt", Color.BLUE, Season.AUTUMN, 5, false)
        ));
        expected.put("Green shirt", List.of(
                new Product(4, "Green shirt", Color.GREEN, Season.SPRING, 11, false)
        ));


        Map<String, List<Product>> result = productBrowser.groupByName();


        for(Map.Entry<String, List<Product>> entry : expected.entrySet()){
            assertArrayEquals(result.get(entry.getKey()).toArray(), entry.getValue().toArray());
        }

    }

    @Test
    void groupByColor() {
        List<Product> expectedProducts = List.of(
                new Product(1, "Yellow shirt", Color.YELLOW, Season.SUMMER, 7, false),
                new Product(2, "Red shirt", Color.RED, Season.SPRING, 10, true),
                new Product(3, "Blue shirt", Color.BLUE, Season.AUTUMN, 5, false),
                new Product(4, "Green shirt", Color.GREEN, Season.SPRING, 11, false),
                new Product(5, "Yellow shirt", Color.YELLOW, Season.SPRING, 3, false)
        );
        when(productProviderMock.getProducts()).thenReturn(
                expectedProducts
        );

        ProductBrowser productBrowser = new ProductBrowserImpl(productProviderMock);

        Map<Color, List<Product>> expected = new HashMap<>();
        expected.put(Color.YELLOW, List.of(
                new Product(1, "Yellow shirt", Color.YELLOW, Season.SUMMER, 7, false),
                new Product(5, "Yellow shirt", Color.YELLOW, Season.SPRING, 3, false)
        ));
        expected.put(Color.RED, List.of(
                new Product(2, "Red shirt", Color.RED, Season.SPRING, 10, true)
        ));
        expected.put(Color.BLUE, List.of(
                new Product(3, "Blue shirt", Color.BLUE, Season.AUTUMN, 5, false)
        ));
        expected.put(Color.GREEN, List.of(
                new Product(4, "Green shirt", Color.GREEN, Season.SPRING, 11, false)
        ));


        Map<Color, List<Product>> result = productBrowser.groupByColor();


        for(Map.Entry<Color, List<Product>> entry : expected.entrySet()){
            assertArrayEquals(result.get(entry.getKey()).toArray(), entry.getValue().toArray());
        }
    }

    @Test
    void groupBySeason() {
        List<Product> expectedProducts = List.of(
                new Product(1, "Yellow shirt", Color.YELLOW, Season.SUMMER, 7, false),
                new Product(2, "Red shirt", Color.RED, Season.SPRING, 10, true),
                new Product(3, "Blue shirt", Color.BLUE, Season.AUTUMN, 5, false),
                new Product(4, "Green shirt", Color.GREEN, Season.SPRING, 11, false),
                new Product(5, "Yellow shirt", Color.YELLOW, Season.SPRING, 3, false)
        );
        when(productProviderMock.getProducts()).thenReturn(
                expectedProducts
        );

        ProductBrowser productBrowser = new ProductBrowserImpl(productProviderMock);

        Map<Season, List<Product>> expected = new HashMap<>();
        expected.put(Season.SUMMER, List.of(
                new Product(1, "Yellow shirt", Color.YELLOW, Season.SUMMER, 7, false)
        ));
        expected.put(Season.SPRING, List.of(
                new Product(2, "Red shirt", Color.RED, Season.SPRING, 10, true),
                new Product(4, "Green shirt", Color.GREEN, Season.SPRING, 11, false),
                new Product(5, "Yellow shirt", Color.YELLOW, Season.SPRING, 3, false)
        ));
        expected.put(Season.AUTUMN, List.of(
                new Product(3, "Blue shirt", Color.BLUE, Season.AUTUMN, 5, false)
        ));


        Map<Season, List<Product>> result = productBrowser.groupBySeason();


        for(Map.Entry<Season, List<Product>> entry : expected.entrySet()){
            assertArrayEquals(result.get(entry.getKey()).toArray(), entry.getValue().toArray());
        }
    }

    @Test
    void groupByPriceRange() {
        List<Product> expectedProducts = List.of(
                new Product(1, "Yellow shirt", Color.YELLOW, Season.SUMMER, 7, false),
                new Product(2, "Red shirt", Color.RED, Season.SPRING, 10, true),
                new Product(3, "Blue shirt", Color.BLUE, Season.AUTUMN, 5, false),
                new Product(4, "Green shirt", Color.GREEN, Season.SPRING, 11, false),
                new Product(5, "Yellow shirt", Color.YELLOW, Season.SPRING, 3, false)
        );
        when(productProviderMock.getProducts()).thenReturn(
                expectedProducts
        );

        ProductBrowser productBrowser = new ProductBrowserImpl(productProviderMock);

        PriceRange low = new PriceRange(3, 6);
        PriceRange medium = new PriceRange(6, 9);
        PriceRange high = new PriceRange(9, 12);


        Map<PriceRange, List<Product>> expected = new HashMap<>();
        expected.put(low, List.of(
                new Product(3, "Blue shirt", Color.BLUE, Season.AUTUMN, 5, false),
                new Product(5, "Yellow shirt", Color.YELLOW, Season.SPRING, 3, false)
        ));
        expected.put(medium, List.of(
                new Product(1, "Yellow shirt", Color.YELLOW, Season.SUMMER, 7, false)
        ));
        expected.put(high, List.of(
                new Product(2, "Red shirt", Color.RED, Season.SPRING, 10, true),
                new Product(4, "Green shirt", Color.GREEN, Season.SPRING, 11, false)
        ));



        Map<PriceRange, List<Product>> result = productBrowser.groupByPriceRange();

        for(Map.Entry<PriceRange, List<Product>> entry : expected.entrySet()){
            assertArrayEquals(result.get(entry.getKey()).toArray(), entry.getValue().toArray());
        }
    }

    @Test
    void orderByName() {
    }

    @Test
    void orderByColor() {
    }

    @Test
    void orderBySeason() {
    }

    @Test
    void orderByPrice() {
    }
}