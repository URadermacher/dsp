package eu.vdmr.math.vector;

import eu.vdmr.math.testData.VectorTestData;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VectorTest {

    @Test
    void testCreation() {
        Vector v = Vector.createVector(2);
        double[] data = v.getData();
        assertThat(data).hasSize(2);
        assertThat(data[0] = 0.0);
    }

    @Test
    void testCreationIllegalArgumet() {
        assertThatThrownBy(() -> Vector.createVector(2, 22.0, 33.0, 44.0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot accept 3 data for vector of size 2");
    }

    @Test
    void testCreationFromTemplate() {
        Vector v1 = VectorTestData.getEx1();
        Vector v2 = Vector.createVector(v1);
        assertThat(v1).isNotSameAs(v2);
        assertThat(v1.equals(v2)).isTrue();
    }

    @Test
    void testCreationArray() {
        double[] input = new double[] {1.0, 2.0, 3.0, 4.0, 5.0};
        Vector u = Vector.createVector(input);
        assertThat(u.getDimension()).isEqualTo(5);
        assertThat(u.getData()).isEqualTo(input);
    }


    @Test
    void testEqualsTrue() {
        Vector v1 = VectorTestData.getEx1();
        Vector v2 = VectorTestData.getEx1();
        assertThat(v1).isNotSameAs(v2);
        assertThat(v1.equals(v2)).isTrue();
    }

    @Test
    void testEqualsFalseSize() {
        Vector v1 = Vector.createVector(2);
        Vector v2 = Vector.createVector(3);
        assertThat(v1.equals(v2)).isFalse();
    }

    @Test
    void testEqualsFalseData() {
        Vector v1 = Vector.createVector(3, 2.0, 33.0, 12.0);
        Vector v2 = Vector.createVector(3, 2.0, 32.0, 12.0);
        assertThat(v1.equals(v2)).isFalse();
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    void testEqualsFalseObject() {
        Vector v1 = Vector.createVector(3, 2.0, 33.0, 12.0);
        assertThat(v1.equals("Hello world")).isFalse();
    }

    @Test
    void testAdditionNotAllowed() {
        Vector u = Vector.createVector(2);
        Vector v = Vector.createVector(3);
        assertThatThrownBy(() -> u.add(v))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot add Vectors of different dimension 2 v 3");
    }

    @Test
    void testAddition() {
        Vector u = VectorTestData.getEx1();
        Vector v = VectorTestData.getEx1();
        Vector x = u.add(v);
        assertThat(x).isNotSameAs(u);
        assertThat(x.getData()).isEqualTo(new double[] {4, 66.0 , 24.0});

    }

    @Test
    void testScalarMultiplication() {
        Vector u = VectorTestData.getEx1();
        Vector v = u.multiply(0.5);
        assertThat(v).isNotSameAs(u);
        assertThat(v.getData()).isEqualTo(new double[] {1, 16.5 , 6.0});
     }

    @Test
    void testLinearCombination() {
        Vector b = Vector.createVector(3, 7.0, 4.0, -3.0);
        Vector a1 = Vector.createVector(3, 1.0, -2.0, -5.0);
        Vector a2 = Vector.createVector(3, 2.0, 5.0, 6.0);
        assertThat(b.isLinearCombinationOf(a1, a2)).isTrue();
     }

    @Test
    void testInSpanFalse() {
        Vector b = Vector.createVector(3, -3.0, 8.0, 1.0);
        Vector a1 = Vector.createVector(3, 1.0, -2.0, 3.0);
        Vector a2 = Vector.createVector(3, 5.0, -13.0, -3.0);
        assertThat(b.isLinearCombinationOf(a1, a2)).isFalse();
    }

    @Test
    void testToString() {
        Vector u = VectorTestData.getEx1();
        assertThat(u.toString()).isEqualTo("(2.0, 33.0, 12.0)");
    }

    @Test
    void testIsInSpanFalse() {
        Vector b = Vector.createVector(3, -3.0, 8.0, 1.0);
        Vector a1 = Vector.createVector(3, 1.0, -2.0, 3.0);
        Vector a2 = Vector.createVector(3, 5.0, -13.0, -3.0);
        assertThat(b.isInSpan(a1, a2)).isFalse();
    }

    @Test
    void testIsInSpanTrue() {
        Vector b = Vector.createVector(3, 16.0, -41.0, -6.0);
        Vector a1 = Vector.createVector(3, 1.0, -2.0, 3.0);
        Vector a2 = Vector.createVector(3, 5.0, -13.0, -3.0);
        assertThat(b.isInSpan(a1, a2)).isTrue();
    }
}
