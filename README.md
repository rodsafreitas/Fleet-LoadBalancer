# 🚀 Fleet LoadBalancer

Fleet LoadBalancer is a load balancer built with Java and Spring Boot.
The goal of this project is to simulate and implement different load balancing strategies to distribute requests across multiple service instances.

The system selects which service instance should receive the next request based on the configured load balancing strategy.

## ⚙️ How It Works

Fleet LoadBalancer receives a request and decides which server should handle it.

The process works like this:

1. The application maintains a list of available service addresses.
2. A load balancing strategy is selected.
3. When a request arrives:
   - The strategy chooses the next server.
   - The request is forwarded to that server.

This design makes it easy to add new load balancing algorithms without changing the rest of the application, following SOLID principles.

### ⚙️ server.conf 

Fleet LoadBalancer uses a configuration file called server.conf to define the servers and choose the algorithm.

This file allows you to configure how traffic should be distributed between service instances.

Example:
```conf
server {
    location {
        http://localhost:3001 weight=3,
        http://localhost:3002 weight=2,
        https://jsonplaceholder.typicode.com weight=4 
    }
}
```
If you don't want to use Weighted Round Robin strategy you can simply remove the weight parameter and the selected algorithm will be Round Robin, like this:
```conf
server {
    location {
        http://localhost:3001,
        http://localhost:3002,
        https://jsonplaceholder.typicode.com
    }
}
```
### Configuration Rules

- If `weight` is present → **Weighted Round Robin**
- If `weight` is not present → **Round Robin**

## 🧠 Architecture

The project is structured using a Strategy Pattern, allowing different balancing algorithms to be plugged in dynamically.

Main components:

- **Controller**  
  Receives incoming HTTP requests.

- **LoadBalancerService**  
  Responsible for delegating the request to the correct server.

- **StrategyFactory**  
  Decides which load balancing strategy will be used.

- **LoadBalancingStrategy (Interface)**  
  Defines the contract that every balancing algorithm must implement.

Strategies

- Round Robin

- Weighted Round Robin

This structure keeps the code clean, extensible, and easy to maintain.

## 🎯 Features
### 🔁 Round Robin Strategy

Distributes requests sequentially across all available servers.

Example with 3 servers:

- Request 1 → Server A
- Request 2 → Server B
- Request 3 → Server C
- Request 4 → Server A

This ensures a fair distribution of requests.

### ⚖️ Weighted Round Robin Strategy

Allows servers to receive traffic based on a weight value, which represents their capacity.

Example:

- Server A → weight 2
- Server B → weight 1

Distribution:

- Request 1 → Server A
- Request 2 → Server A
- Request 3 → Server B
- Request 4 → Server A
- Request 5 → Server A
- Request 6 → Server B

This is useful when some servers are more powerful than others.
