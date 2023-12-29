package com.codecool.seasonalproductdiscounter;

import com.codecool.seasonalproductdiscounter.model.products.Product;
import com.codecool.seasonalproductdiscounter.model.transactions.Transaction;
import com.codecool.seasonalproductdiscounter.model.transactions.TransactionsSimulatorSettings;
import com.codecool.seasonalproductdiscounter.service.authentication.AuthenticationService;
import com.codecool.seasonalproductdiscounter.service.authentication.AuthenticationServiceImpl;
import com.codecool.seasonalproductdiscounter.service.discounts.DiscountProvider;
import com.codecool.seasonalproductdiscounter.service.discounts.DiscountProviderImpl;
import com.codecool.seasonalproductdiscounter.service.discounts.DiscountService;
import com.codecool.seasonalproductdiscounter.service.discounts.DiscountServiceImpl;
import com.codecool.seasonalproductdiscounter.service.logger.ConsoleLogger;
import com.codecool.seasonalproductdiscounter.service.logger.Logger;
import com.codecool.seasonalproductdiscounter.service.persistence.DatabaseManager;
import com.codecool.seasonalproductdiscounter.service.persistence.DatabaseManagerImpl;
import com.codecool.seasonalproductdiscounter.service.persistence.SqliteConnector;
import com.codecool.seasonalproductdiscounter.service.products.provider.RandomProductGenerator;
import com.codecool.seasonalproductdiscounter.service.products.repository.ProductRepository;
import com.codecool.seasonalproductdiscounter.service.products.repository.ProductRepositoryImpl;
import com.codecool.seasonalproductdiscounter.service.transactions.repository.TransactionRepository;
import com.codecool.seasonalproductdiscounter.service.transactions.simulator.TransactionsSimulator;
import com.codecool.seasonalproductdiscounter.service.users.UserRepository;

import java.io.Console;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws SQLException {

        Logger logger = new ConsoleLogger();

        String dbFile = "src/main/resources/SeasonalProductDiscounter.db";
        SqliteConnector sqliteConnector = new SqliteConnector(dbFile, logger);
        sqliteConnector.getConnection();

        DatabaseManager dbManager = new DatabaseManagerImpl(sqliteConnector, logger);

        DiscountProvider discountProvider = new DiscountProviderImpl();
        DiscountService discounterService = new DiscountServiceImpl(discountProvider);
        AuthenticationService authenticationService = new AuthenticationServiceImpl();

        ProductRepository productRepository = new ProductRepositoryImpl(logger,sqliteConnector);
        UserRepository userRepository = null;
        TransactionRepository transactionRepository = null;

        dbManager.createTables();
        initializeDatabase(productRepository);

        TransactionsSimulator simulator = new TransactionsSimulator(logger, userRepository, productRepository,
                authenticationService, discounterService, transactionRepository);

        //RunSimulation(simulator, productRepository, transactionRepository);

        System.out.println("Press any key to exit.");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

    }

    private static void initializeDatabase(ProductRepository productRepository) {
        if (productRepository.getAvailableProducts().isEmpty()) {
            //Add products to the repo
            RandomProductGenerator randomProductGenerator = new RandomProductGenerator(1000, 20, 80);
            List<Product> randomProducts = randomProductGenerator.getProducts();
            productRepository.addProducts(randomProducts);
        }
    }

    private static void RunSimulation(TransactionsSimulator simulator, ProductRepository productRepository,
                                      TransactionRepository transactionRepository) {
        int days = 0;
        LocalDate date = LocalDate.now();

        // set your own condition
        while (true) {
            System.out.println("Starting simulation...");
            simulator.run(new TransactionsSimulatorSettings(date, 100, 70));

            List<Transaction> transactions = transactionRepository.getAll();
            System.out.println(date + " ended, total transactions: " + transactions.size() + ", total income: "
                    + transactions.stream().mapToDouble(Transaction::pricePaid).sum());
            System.out.println("Products left to sell: " + productRepository.getAvailableProducts().size());
        }
    }


}
