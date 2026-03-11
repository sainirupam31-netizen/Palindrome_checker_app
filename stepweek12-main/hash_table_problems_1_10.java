// Complete Hash Table & System Design Solutions (Problems 1–10)
// RA24111003012479
// SANAPALLI RS S VENUGOPAL
// R2

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

// ---------------- Problem 1: Social Media Username Availability Checker ----------------
class UsernameChecker {
    Map<String, Integer> usernameMap = new HashMap<>();
    Map<String, Integer> attemptFrequency = new HashMap<>();

    boolean checkAvailability(String username) {
        attemptFrequency.put(username, attemptFrequency.getOrDefault(username, 0) + 1);
        return !usernameMap.containsKey(username);
    }

    List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();
        int i = 1;
        while(suggestions.size() < 3) {
            String newName = username + i;
            if(!usernameMap.containsKey(newName)) suggestions.add(newName);
            i++;
        }
        return suggestions;
    }

    String getMostAttempted() {
        return Collections.max(attemptFrequency.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}

// ---------------- Problem 2: E-commerce Flash Sale Inventory Manager ----------------
class InventoryManager {
    Map<String, AtomicInteger> stockMap = new HashMap<>();
    Map<String, Queue<Integer>> waitingList = new HashMap<>();

    synchronized boolean purchaseItem(String productId, int userId) {
        if(stockMap.get(productId).get() > 0) {
            stockMap.get(productId).decrementAndGet();
            return true;
        } else {
            waitingList.computeIfAbsent(productId, k -> new LinkedList<>()).add(userId);
            return false;
        }
    }

    int checkStock(String productId) {
        return stockMap.get(productId).get();
    }
}

// ---------------- Problem 3: DNS Cache with TTL ----------------
class DNSEntry {
    String domain, ipAddress;
    long expiryTime;

    DNSEntry(String domain, String ip, long ttlSeconds) {
        this.domain = domain;
        this.ipAddress = ip;
        this.expiryTime = System.currentTimeMillis() + ttlSeconds*1000;
    }

    boolean isExpired() { return System.currentTimeMillis() > expiryTime; }
}

class DNSCache {
    Map<String, DNSEntry> cache = new HashMap<>();

    String resolve(String domain, long ttl) {
        DNSEntry entry = cache.get(domain);
        if(entry == null || entry.isExpired()) {
            String ip = queryUpstreamDNS(domain);
            cache.put(domain, new DNSEntry(domain, ip, ttl));
            return ip;
        }
        return entry.ipAddress;
    }

    String queryUpstreamDNS(String domain) { return "172.217.14.206"; }
}

// ---------------- Problem 4: Plagiarism Detection System ----------------
class PlagiarismDetector {
    Map<String, Set<String>> ngramMap = new HashMap<>();

    void processDocument(String docId, List<String> ngrams) {
        for(String ngram: ngrams) ngramMap.computeIfAbsent(ngram, k -> new HashSet<>()).add(docId);
    }

    double calculateSimilarity(List<String> ngrams, String otherDocId) {
        int match=0;
        for(String ngram: ngrams)
            if(ngramMap.getOrDefault(ngram, new HashSet<>()).contains(otherDocId)) match++;
        return 100.0*match/ngrams.size();
    }
}

// ---------------- Problem 5: Real-Time Analytics Dashboard ----------------
class AnalyticsDashboard {
    Map<String,Integer> pageViews = new HashMap<>();
    Map<String,Set<String>> uniqueVisitors = new HashMap<>();
    Map<String,Integer> trafficSources = new HashMap<>();

    void processEvent(String url,String userId,String source){
        pageViews.put(url,pageViews.getOrDefault(url,0)+1);
        uniqueVisitors.computeIfAbsent(url,k->new HashSet<>()).add(userId);
        trafficSources.put(source,trafficSources.getOrDefault(source,0)+1);
    }
}

// ---------------- Problem 6: Distributed Rate Limiter ----------------
class TokenBucket {
    int tokens,maxTokens;
    long lastRefillTime,refillRateMs;

    TokenBucket(int maxTokens,long refillRateMs){
        this.maxTokens=maxTokens;
        this.tokens=maxTokens;
        this.refillRateMs=refillRateMs;
        lastRefillTime=System.currentTimeMillis();
    }

    synchronized boolean allowRequest(){
        long now=System.currentTimeMillis();
        int refill=(int)((now-lastRefillTime)/refillRateMs);
        tokens=Math.min(maxTokens,tokens+refill);
        lastRefillTime=now;
        if(tokens>0){tokens--;return true;} else return false;
    }
}

// ---------------- Problem 7: Autocomplete System ----------------
class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEnd = false;
    int frequency = 0;
}

class AutocompleteSystem {
    TrieNode root = new TrieNode();

    void insert(String query) {
        TrieNode node = root;
        for(char c: query.toCharArray()) {
            node = node.children.computeIfAbsent(c, k->new TrieNode());
        }
        node.isEnd = true;
        node.frequency++;
    }

    List<String> getSuggestions(String prefix) {
        TrieNode node = root;
        for(char c: prefix.toCharArray()) {
            if(!node.children.containsKey(c)) return new ArrayList<>();
            node = node.children.get(c);
        }
        List<String> results = new ArrayList<>();
        dfs(node, prefix, results);
        return results;
    }

    void dfs(TrieNode node, String word, List<String> results) {
        if(node.isEnd) results.add(word);
        for(char c: node.children.keySet()) {
            dfs(node.children.get(c), word+c, results);
        }
    }
}

// ---------------- Problem 8: Parking Lot Management ----------------
class ParkingLot {
    String[] spots;
    int capacity;

    ParkingLot(int capacity) {
        this.capacity = capacity;
        spots = new String[capacity];
    }

    int hash(String license) { return Math.abs(license.hashCode()) % capacity; }

    int parkVehicle(String license) {
        int index = hash(license);
        while(spots[index]!=null){ index = (index+1)%capacity; }
        spots[index] = license;
        return index;
    }

    void exitVehicle(String license){
        for(int i=0;i<capacity;i++){
            if(license.equals(spots[i])) { spots[i]=null; break; }
        }
    }
}

// ---------------- Problem 9: Two-Sum Financial Transactions ----------------
class Transaction {
    int id, amount;
    String merchant;
    Transaction(int id,int amount,String merchant){this.id=id;this.amount=amount;this.merchant=merchant;}
}

class TwoSumTransactions {
    static List<int[]> findTwoSum(List<Transaction> txs,int target){
        Map<Integer,Integer> map = new HashMap<>();
        List<int[]> res = new ArrayList<>();
        for(Transaction tx: txs){
            int complement = target - tx.amount;
            if(map.containsKey(complement)) res.add(new int[]{map.get(complement),tx.id});
            map.put(tx.amount,tx.id);
        }
        return res;
    }
}

// ---------------- Problem 10: Multi-Level Cache ----------------
class MultiLevelCache {
    LinkedHashMap<String,String> L1;
    Map<String,String> L2;
    Map<String,String> L3;
    int L1Capacity;

    MultiLevelCache(int capacity){
        L1Capacity=capacity;
        L1=new LinkedHashMap<String,String>(capacity,0.75f,true){
            protected boolean removeEldestEntry(Map.Entry<String,String> e){ return size()>L1Capacity; }
        };
        L2=new HashMap<>();
        L3=new HashMap<>();
    }

    String getVideo(String id){
        if(L1.containsKey(id)) return L1.get(id);
        if(L2.containsKey(id)){ L1.put(id,L2.get(id)); return L2.get(id); }
        if(L3.containsKey(id)){ L2.put(id,L3.get(id)); return L3.get(id); }
        return null;
    }

    void addVideo(String id,String data){
        L3.put(id,data);
    }
}