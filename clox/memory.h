#if !defined(clox_memory_h)
#define clox_memory_h

#include "common.h"

void* reallocate(void* ptr, size_t old_size, size_t new_size);

#endif // clox_memory_h
