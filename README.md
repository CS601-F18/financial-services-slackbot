# Financial Services Slackbot

## Overview
The Financial Services slackbot provides a wide range of finance related features a slack user can take advantage of to improve their financial well being and record keeping. The user is able to record transactions and stock purchases by recording them in slack through a variety of slash commands, after the slackbot is installed to the specified workspace.  

Once the transactions are logged, the user can log into the web application to view a history of their transactions, which are stored in an external database.
## Technical Requirements
The financial services slackbot requires a web front end for the user to view their transactions and stocks. This must be deployed to a server that supports Java, and can be deployed by creating a .jar file that launches the server via the "StartServer.java" class. It also requires a slack workspace that has the slackbot installed. Finally, it requires a SQL database. The login credentials and database information can be configured in the DBConstants file. The database schema requires a "transactions", "spusers", "stockown", and "stocksearch" database tables.
## Installation
To use the slackbot, it must be installed to the desired workplace. You can install it to the workplace by launching or deploying the application and accessing /authorize. Follow the on screen instructions to add it your workspace.
## Features
The slackbot's features can be categorized by slash commands. These are configured via the slack API, which sends requests to the specified API endpoints to engage the application. The currently supported slash commands include:

* /transaction {operation} {item} for ${cost}
* /stock {ticker symbol}
* /stocktransaction {operation} {ticket symbol} {number of shares}
* /stocksuggestions
* /bitcoin

### Transactions Ledger
Record your transactions by using the slash command /transaction followed by the operation (charge or credit, buy or sell), followed by the item you've purchased (food, lunch, etc) followed by the word "for", followed by a "$" symbol, followed by the amount of the transaction. You can access your transaction history by signing into your financial services dashboard.
### Real Time stock search
Get the real time trading value of a stock in USD by using the slash command /stock followed by the ticker symbol (fb, msft, etc). You can retrieve your past searches by using /stocksuggestions.
### Stock Transactions Ledger
Record your stock purchases or sales by using the slash command /stocktransaction followed by the operation (buy or sell), followed by the stock ticker symbol(fb, msft, etc.), followed by the number of shares. You can access your stocks by signing into your financial services dashboard.
### Real Time Stock Price Checking
Record your stock purchases or sales by using the slash command /stocktransaction followed by the operation (buy or sell), followed by the stock ticker symbol(fb, msft, etc.), followed by the number of shares. You can access your stocks by signing into your financial services dashboard.
### Stock Suggestions
Get your past stock searches that you've searched for with the /stock slash command.
### Real Time Bitcoin Price Checking
Get the real time trading value of bitcoin in USD.
## Future Updates
Future updates include ability to purchase and sell stocks, improvements to the web interface frontend, and a more forigiving syntax for slash commands.
