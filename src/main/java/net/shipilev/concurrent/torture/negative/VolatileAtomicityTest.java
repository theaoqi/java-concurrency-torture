package net.shipilev.concurrent.torture.negative;

import net.shipilev.concurrent.torture.Outcome;
import net.shipilev.concurrent.torture.TwoActorsOneArbiterTest;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileAtomicityTest extends TwoActorsOneArbiterTest<VolatileAtomicityTest.Specimen> {

    public static class Specimen {
        volatile int x;
        final AtomicInteger count = new AtomicInteger();
    }

    @Override
    public void actor1(Specimen s) {
        s.x++;
        s.count.incrementAndGet();
    }

    @Override
    public void actor2(Specimen s) {
        s.x++;
        s.count.incrementAndGet();
    }

    @Override
    public void arbitrate(Specimen s, byte[] result) {
        result[0] = (byte) s.x;
        result[1] = (byte) s.count.get();
    }

    @Override
    public Specimen createNew() {
        return new Specimen();
    }

    @Override
    protected Outcome test(byte[] result) {
        if (result[0] != result[1]) return Outcome.NOT_EXPECTED;
        return Outcome.ACCEPTABLE;
    }

    public int resultSize() {
        return 2;
    }

}
