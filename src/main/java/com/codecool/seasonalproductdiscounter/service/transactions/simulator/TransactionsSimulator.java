package com.codecool.seasonalproductdiscounter.service.transactions.simulator;

import com.codecool.seasonalproductdiscounter.model.offers.Offer;
import com.codecool.seasonalproductdiscounter.model.products.Product;
import com.codecool.seasonalproductdiscounter.model.transactions.Transaction;
import com.codecool.seasonalproductdiscounter.model.transactions.TransactionsSimulatorSettings;
import com.codecool.seasonalproductdiscounter.model.users.User;
import com.codecool.seasonalproductdiscounter.service.authentication.AuthenticationService;
import com.codecool.seasonalproductdiscounter.service.discounts.DiscountService;
import com.codecool.seasonalproductdiscounter.service.logger.Logger;
import com.codecool.seasonalproductdiscounter.service.products.repository.ProductRepository;
import com.codecool.seasonalproductdiscounter.service.transactions.repository.TransactionRepository;
import com.codecool.seasonalproductdiscounter.service.users.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class TransactionsSimulator {
    private static final Random RANDOM = new Random();

    private final Logger logger;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final AuthenticationService authenticationService;
    private final DiscountService discounterService;
    private final TransactionRepository transactionRepository;

    public TransactionsSimulator(Logger logger, UserRepository userRepository,
                                 ProductRepository productRepository, AuthenticationService authenticationService,
                                 DiscountService discounterService, TransactionRepository transactionRepository) {
        this.logger = logger;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.authenticationService = authenticationService;
        this.discounterService = discounterService;
        this.transactionRepository = transactionRepository;
    }

    public void run(TransactionsSimulatorSettings settings) {
        int successfulTransactions = 0;
        int rounds = 0;
        if(deletingUsersTransactionsTable()){
            logger.logInfo("All records have been deleted!");
        } else{
            logger.logInfo("Something went wrong! Couldn't delete records!");
        }

        if(settingProductsUnsold()){
            logger.logInfo("All products set to unsold!");
        } else{
            logger.logError("Couldn't reset products to unsold!");
        }

        logger.logInfo("Starting simulation");
        while (successfulTransactions <= settings.transactionsCount()) {
            logger.logInfo(String.format("Simulation round #%d, successful transactions: %d/%d",
                    rounds++, successfulTransactions, settings.transactionsCount()));

            User user = getRandomUser(settings.usersCount());
            logger.logInfo(String.format("User [%s] looking to buy a product", user.userName()));

            if (!authUser(user)) {
                if(registerUser(user)){
                    logger.logInfo(user.userName() + " Is successfully registered!");
                } else{
                    logger.logError(user.userName() + " Couldn't registrate!");
                }
            }

            user = getUserFromRepo(user.userName());
            if(user == null){
                logger.logError(user.userName() + " is not found!");
            }

            Product product = selectProduct();


            if (product == null) {
                logger.logError("Unfortunately we have ran out of stock! Please check later!");
                break;
            }

            Offer offer = getOffer(product, settings.date());

            Transaction transaction = createTransaction(settings.date(), user, product, offer.price());

            if (saveTransaction(transaction)) {
                setProductAsSold(product);
                successfulTransactions++;
            }
        }
    }

    private boolean settingProductsUnsold(){
        return productRepository.setSoldToUnsold();
    }

    private boolean deletingUsersTransactionsTable(){
        boolean removingUsers = userRepository.removeData();
        boolean removingTransactions = transactionRepository.removeData();

        return removingTransactions == removingUsers;
    }

    private User getRandomUser(int usersCount) {
        int newUserId = userRepository.getUsers().size() + 1;
        return new User(newUserId, String.format("user%d", RANDOM.nextInt(usersCount)), "pw");
    }

    private User getUserFromRepo(String username) {
        return userRepository.getUserByUserName(username);
    }

    private boolean authUser(User user) {
        return authenticationService.authenticate(user);
    }

    private boolean registerUser(User user) {
        return userRepository.addUser(user);
    }

    private Product selectProduct() {
        return getRandomProduct();
    }

    private Product getRandomProduct() {
        List<Product> allProducts = productRepository.getAvailableProducts();

        if (allProducts.isEmpty()) {
            return null;
        }

        return allProducts.get(RANDOM.nextInt(allProducts.size()));
    }

    private Offer getOffer(Product product, LocalDate date) {
        return discounterService.getOffer(product, date);
    }

    private Transaction createTransaction(LocalDate date, User user, Product product, double price) {
        int newTransactionId = transactionRepository.getAll().size() + 1;
        return new Transaction(newTransactionId, date, user, product, price);
    }

    private boolean saveTransaction(Transaction transaction) {
        return transactionRepository.add(transaction);
    }

    private boolean setProductAsSold(Product product) {
        return productRepository.setProductAsSold(product);
    }
}

