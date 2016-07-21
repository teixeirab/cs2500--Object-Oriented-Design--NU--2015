import java.util.ArrayList;
import java.util.List;

/**
 * Created by becogontijo on 4/15/2015.
 */
public final class Permutation {
  private final int[] perm;

  private Permutation(int[] perm){
    this.perm = perm; //use with caution
  }

  public static Permutation identity(int size){
    int [] newPerm = new int [size];

    for (int i = 0; i < size; ++i){
      newPerm[i] = i;
    }

    return new Permutation(newPerm);
  }

  public Permutation(Integer[] indices){
    int size = indices.length;
    perm = new int[size];

    for (int i = 0; i < size; ++i){
      int to = indices[i];

      if (to < 0 || to >= size){
        throw new IllegalArgumentException("out of range");
      }

      for (int seen : perm){
        if (to == seen){
          throw new IllegalArgumentException("repeated");
        }
      }
    perm[i] = to;
    }



  }

  public <E> ArrayList<E> apply (List<E> elements){
    if (elements.size() != perm.length){
      throw new IllegalArgumentException("size dont match");
    }
    ArrayList<E> dst = new ArrayList<>(elements);

    int from = 0;
    for (E element : elements){
      dst.set(perm[from++], element);
    }
    return dst;

   }

  public Permutation invert(){
    int[] newPerm = new int[perm.length];

    for (int i = 0; i < perm.length; ++i){
      newPerm[perm[i]] = 1;
    }
    return new Permutation(newPerm);
  }
}
