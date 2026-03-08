package br.com.balancer.fleet.strategy;

import br.com.balancer.fleet.model.Address;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinStrategy implements LoadBalancingStrategy {

    private final List<Address> addressList;
    private final AtomicInteger currentIndex = new AtomicInteger(0);

    public RoundRobinStrategy(List<Address> addressList) {

        if (addressList == null || addressList.isEmpty()) {
            throw new IllegalArgumentException("Address list cannot be null or empty");
        }

        this.addressList = List.copyOf(addressList);
    }

    @Override
    public String getNextService() {
        int current = currentIndex.getAndIncrement();
        int index = Math.abs(current % addressList.size());
        return addressList.get(index).url();

    }
}
