package com.loonds;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

public class StockMarketAnalysis {
    private static final List<Stock> stocks = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Add Stock Data\n2. List All Stocks\n3. Calculate Moving Average\n4. Find Max Gain\n5. Filter High-Performing Stocks\n6. Exit");
            System.out.print("Enter your choice: ");
            // Check if the input is an integer
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            switch (choice) {
                case 1:
                    addStock(scanner);
                    break;
                case 2:
                    listAllStocks();
                    break;
                case 3:
                    calculateMovingAverage(scanner);
                    break;
                case 4:
                    findMaxGain();
                    break;
                case 5:
                    filterHighPerformingStocks(scanner);
                    break;
                case 6:
                    System.out.println("Exiting the application...");
                    scanner.close(); // Close the scanner before exit
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    // Method to add stock data
    private static void addStock(Scanner scanner) {
        System.out.println("Enter stock name:");
        String name = scanner.nextLine();
        System.out.println("Enter stock price:");

        // Ensure valid double input
        if (!scanner.hasNextDouble()) {
            System.out.println("Invalid price. Please enter a valid number.");
            scanner.next(); // Consume invalid input
            return;
        }
        double price = scanner.nextDouble();

        System.out.println("Enter stock volume:");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid volume. Please enter a valid integer.");
            scanner.next(); // Consume invalid input
            return;
        }
        int volume = scanner.nextInt();

        System.out.println("Enter date (yyyy-mm-dd):");
        scanner.nextLine(); // Clear the buffer
        LocalDate date;
        try {
            date = LocalDate.parse(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid date format. Please try again.");
            return;
        }

        Stock stock = new Stock(name, price, volume, date);
        stocks.add(stock);
        System.out.println("Stock added successfully!");
    }

    // Method to list all stocks
    private static void listAllStocks() {
        if (stocks.isEmpty()) {
            System.out.println("No stock data available.");
        } else {
            stocks.forEach(System.out::println);
        }
    }

    // Method to calculate moving average
    private static void calculateMovingAverage(Scanner scanner) {
        System.out.println("Enter the stock name:");
        String stockName = scanner.nextLine();
        System.out.println("Enter the number of days for the moving average:");

        if (!scanner.hasNextInt()) {
            System.out.println("Invalid number of days. Please enter a valid integer.");
            scanner.next(); // Consume invalid input
            return;
        }
        int days = scanner.nextInt();

        List<Stock> filteredStocks = stocks.stream()
                .filter(stock -> stock.getName().equalsIgnoreCase(stockName))
                .sorted((stock1, stock2) -> stock2.getDate().compareTo(stock1.getDate()))
                .limit(days)
                .toList();

        if (filteredStocks.isEmpty()) {
            System.out.println("No stock data available for the specified stock.");
            return;
        }

        double movingAverage = filteredStocks.stream().mapToDouble(Stock::getPrice).average().orElse(0.0);
        System.out.println("The moving average for " + stockName + " over the last " + days + " days is: " + movingAverage);
    }

    // Method to find max gain (maximum profit)
    private static void findMaxGain() {
        if (stocks.isEmpty()) {
            System.out.println("No stock data available to calculate max gain.");
            return;
        }

        Optional<Stock> minPriceStock = stocks.stream().min(Comparator.comparingDouble(Stock::getPrice));
        Optional<Stock> maxPriceStock = stocks.stream().max(Comparator.comparingDouble(Stock::getPrice));


        if (maxPriceStock.isPresent()) {
            double maxGain = maxPriceStock.get().getPrice() - minPriceStock.get().getPrice();
            System.out.println("The maximum gain is: " + maxGain);
        } else {
            System.out.println("No sufficient data to calculate max gain.");
        }
    }

    // Method to filter high-performing stocks using dynamic criteria
    private static void filterHighPerformingStocks(Scanner scanner) {
        System.out.println("Enter minimum price to filter high-performing stocks:");

        if (!scanner.hasNextDouble()) {
            System.out.println("Invalid price. Please enter a valid number.");
            scanner.next(); // Consume invalid input
            return;
        }
        double minPrice = scanner.nextDouble();

        Predicate<Stock> highPerformingCriteria = stock -> stock.getPrice() > minPrice;
        List<Stock> highPerformingStocks = stocks.stream()
                .filter(highPerformingCriteria)
                .toList();

        if (highPerformingStocks.isEmpty()) {
            System.out.println("No high-performing stocks found.");
        } else {
            System.out.println("High-performing stocks:");
            highPerformingStocks.forEach(System.out::println);
        }
    }
}
