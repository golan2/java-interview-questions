package interview.javajava;

import java.util.HashMap;
import java.util.Map;

public class PolynomialStuff {


    public static void main(String[] args) {

        //11x^5 + 3x^4 + 6x^3 + 2x^2 + 4x + 1
        new Polynom()
                .add(new Term(11, 5))
                .add(new Term(3, 4))
                .add(new Term(6, 3))
                .add(new Term(2, 2))
                .add(new Term(4, 1))
                .add(new Term(1, 0));
    }

    private static class Polynom {
        final Map<Integer, Term> terms = new HashMap<>();

        public Polynom add(Term term) {
            final Term existing = this.terms.get(term.exponent);
            if (existing==null) {
                this.terms.put(term.exponent, term);
            }
            else {
                this.terms.put(term.exponent, new Term(term.exponent, existing.coefficient+term.coefficient));
            }
            return this;
        }

        public void add(Polynom poly) {
            poly.terms.forEach((key, value) -> add(value));
        }



    }

    private static class Term {
        private final int    exponent;
        private final double coefficient;

        Term(int exponent, double coefficient) {
            this.coefficient = coefficient;
            this.exponent = exponent;
        }
    }
}
