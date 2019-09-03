## Insertion

```
insert d, e, c, b, a, after a inserted, this tree become imbalanced, 

                d
             /    \
            c      e
          /     
         b   
        /
       a

so when stack pops up from c node, a rebalance is fired, then we have the following:
                     
                     d
                   /    \
                  b      e
                /   \
               a     c
```
