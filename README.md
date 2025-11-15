# Coffee Shop

**Final project for the course "Software Design Patterns"**  
**Instructor: Dinmukhammed Temirgaly**  
**Student: Rakhman Seidigali (Group SE-2411)**

Educational console application demonstrating 7 classic design patterns in a compact domain: coffee shop ordering, drink customization, seasonal menu, discount strategies, event notifications, and multiple payment systems.

Patterns used:
1. Abstract Factory (menu/product families)
2. Builder (incremental order construction)
3. Facade (workflow orchestration)
4. Strategy (pricing algorithms)
5. Decorator (dynamic drink toppings)
6. Observer (order status events)
7. Adapter (unified payment interface)

---
## Contents
1. Features
2. Quick Start
3. Example Session
4. Architecture & Patterns
5. Directory Layout
6. License

---
## 1. Features
- Step-by-step order creation (Builder + Facade)
- Base and seasonal items (Abstract Factory: `StandardMenuFactory`, `AutumnMenuFactory`)
- Drink customization (Decorator: milk, syrup, extra shot, whipped cream, cinnamon)
- Pricing strategies with discount breakdown (Strategy: No discount, Student, Happy Hour)
- Event-driven order status updates (Observer: `EventManager`, `BaristaConsoleObserver`, `OrderLogObserver`)
- Pluggable payment providers (Adapter: Cash, Kaspi QR, Halyk Bank)
- Seasonal product families under `model.*.seasonal`

---
## 2. Quick Start
### Requirements
- Java 17+
- Windows PowerShell (commands below). On other OS adjust shell syntax.

### Build
```powershell
cd "C:\Users\jungk\Downloads\Coffee Shop"
if (Test-Path .\out) { Remove-Item -Recurse -Force .\out }
mkdir out
$src = Get-ChildItem -Recurse -Filter *.java | % FullName
javac -encoding UTF-8 -d out $src
```

### Run
```powershell
java -cp .\out app.ConsoleApp
```

---
## 3. Example Session (excerpt)
```
=== Welcome to Coffee Shop ===
Choose menu variant:
1) Standard menu
2) Autumn seasonal menu
Your choice (default: 1): 2
🍂 Autumn menu selected! Seasonal items available.
Choose payment method:
1) Cash
2) Kaspi QR
3) Halyk Bank
Your choice (default: 1): 2

Add item:
Drinks:
 1) Espresso
 ...
🍂 Autumn Specials:
21) Pumpkin Macchiato
Your choice: 21
Quantity: 1
Add milk? (y/n): y
Choose milk type: 2 (Oat)
>> Added to order: Pumpkin Macchiato x1 (990.00 ₸ each)
=== Current order draft ===
- Pumpkin Macchiato x1 = 990.00 ₸
---------------------------
Subtotal (no discounts): 990.00 ₸
Total with Student 10% discount: 891.00 ₸ (−99.00 ₸)
===========================
Proceed to checkout...
Subtotal (no discounts): 990.00 ₸
Discount applied (Student 10% discount): −99.00 ₸
Total to pay: 891.00 ₸
Kaspi: Generated QR-code for payment 891.0
Kaspi: Payment confirmation KSP-...
Your payment of 891.00 ₸ was successful.
Barista prepares your order...
```

---
## 4. Architecture & Patterns
### Abstract Factory
`MenuFactory` plus concrete `StandardMenuFactory` and `AutumnMenuFactory` provide consistent families (Beverage, Dessert, Meal). Seasonal factory delegates unknown codes to standard.
```java
MenuFactory factory = new AutumnMenuFactory();
Beverage b = factory.createBeverage("PUMPKIN_MACCHIATO");
```

### Builder
`OrderBuilder` accumulates items then produces an `Order` (validation: non-empty, positive quantity).
```java
Order order = new OrderBuilder().addItem(menuItem, 2).build();
```

### Facade
`CoffeeShopFacade` exposes high-level operations: start order, add base or customized drink, price, pay, observe status.

### Strategy
`PricingStrategy` interface allows interchangeable discount logic. Draft view shows raw subtotal versus discounted total.
```java
facade.setPricingStrategy(new HappyHourStrategy());
```

### Decorator
Toppings wrap base beverage modifying description and cost without altering concrete classes.
```java
beverage = new MilkDecorator(beverage, milkType);
beverage = new SyrupDecorator(beverage, syrupType);
```

### Observer
`EventManager` manages listeners per string event type. Order emits:
```java
events.notify("order:status_changed", this);
```
Listeners implement:
```java
void update(String eventType, Order order);
```

### Adapter
Unified payment interface:
```java
public interface PaymentProcessor { void processPayment(double amount); }
```
Adapters translate to external APIs (Kaspi QR generation + confirmation, Halyk transaction init + verify).

---
## 5. Directory Layout (simplified)
```
app/                  - entry point (ConsoleApp)
model/                - domain entities (beverage/dessert/meal + seasonal)
patterns/factory/     - Abstract Factory implementations
patterns/builder/     - OrderBuilder
patterns/facade/      - CoffeeShopFacade, DrinkRequest
patterns/decorator/   - beverage decorators & topping types
patterns/strategy/    - pricing strategies
patterns/observer/    - event system & listeners
patterns/adapter/     - payment API & adapters
```

## License
MIT License © 2025 Rakhman Seidigali & Project Contributors

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
