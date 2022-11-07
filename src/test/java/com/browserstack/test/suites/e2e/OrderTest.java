package com.browserstack.test.suites.e2e;

import com.browserstack.app.pages.HomePage;
import com.browserstack.app.pages.OrdersPage;
import com.browserstack.test.suites.TestBase;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("e2e")
public class OrderTest extends TestBase {

    @Test
    public void placeOrder() {
        HomePage page = new HomePage(driver)
                .navigateToSort()
                .addProductToCart("12")
                .navigateToSignIn()
                .loginWith("fav_user", "testingisfun99")
                .openCart()
                .proceedToCheckout()
                .enterShippingDetails("firstname", "lastname", "address", "state", "12345")
                .continueShopping();

        OrdersPage ordersPage = page.navigateToOrders();
        assertEquals(ordersPage.getItemsFromOrder(), 1);
    }
}