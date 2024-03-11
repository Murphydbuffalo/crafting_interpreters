#include <stdlib.h>
#include <stdio.h>
#include "common.h"

void* reallocate(void* ptr, size_t old_size, size_t new_size) {
  if (new_size == 0) {
    free(ptr);
    return NULL;
  } else {
    // If new_size is smaller than old_size, shrinks the allocation
    // If new_size is greater than old_size, will try to grow the allocation
    // if there is memory available adjacent to the existing block.
    // If that's not the case it will allocate an entirely new block
    // of memory of new_size, copy old_size number of values from the
    // old addresses to the new, and then free the old addresses.
    //
    // This is basically the exact behavior used to implement a
    // dynamically resizing array.
    void* result = realloc(ptr, new_size);

    if (result == NULL) {
      // Out of memory!
      exit(1);
    }

    return result;
  }
}

