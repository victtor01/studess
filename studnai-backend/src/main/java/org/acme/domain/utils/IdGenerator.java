package org.acme.domain.utils;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class IdGenerator {
    private final long epoch = 1738778400000L; 
    private final long machineId = 1; // Em prod, isso viria de uma ENV
    
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Relógio retrocedeu!");
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & 4095; // 12 bits para sequência
            if (sequence == 0) {
                timestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        // Estrutura do Bitwise: [1 bit sinal][41 bits tempo][10 bits máquina][12 bits seq]
        return ((timestamp - epoch) << 22) | (machineId << 12) | sequence;
    }

    private long waitNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
    
    // Método para extrair a data do ID gerado
    public long getTimestamp(long id) {
        return (id >> 22) + epoch;
    }
}