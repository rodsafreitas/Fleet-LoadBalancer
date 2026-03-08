package br.com.balancer.fleet.strategy;

import br.com.balancer.fleet.model.Address;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class WeightedRoundRobinStrategy implements LoadBalancingStrategy {

    private final List<String> weightedServerList;
    private final AtomicInteger currentIndex = new AtomicInteger(0);

    public WeightedRoundRobinStrategy(List<Address> addressList) {
        validateAddressList(addressList);
        this.weightedServerList = buildWeightedServerList(addressList);
    }

    private List<String> buildWeightedServerList(List<Address> addressList) {
        List<String> server = new ArrayList<>();

        for (Address address : addressList) {
            for (int i = 0; i < address.weight(); i++) {
                server.add(address.url());
            }
        }

        return List.copyOf(server);

    }

    private void validateAddressList(List<Address> addressList) {
        if (addressList == null || addressList.isEmpty()) {
            throw new IllegalArgumentException("Address List cannot be null or empty");
        }

        for (Address address : addressList) {
            if (address.url() == null || address.url().trim().isEmpty()) {
                throw new IllegalArgumentException("Server URL cannot be null or empty");
            }
            if (address.weight() <= 0) {
                throw new IllegalArgumentException("Server weight must be positive, got: " + address.weight());
            }
        }

    }

    @Override
    public String getNextService() {
        int current = currentIndex.getAndIncrement();
        int serverId = current % weightedServerList.size();
        return weightedServerList.get(serverId);
    }
}
