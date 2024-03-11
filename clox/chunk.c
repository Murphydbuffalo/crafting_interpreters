#include <stdlib.h>
#include "chunk.h"
#include "memory.h"

const int GROWTH_FACTOR = 2;

void initChunk(Chunk* chunk) {
  chunk->count = 0;
  chunk->capacity = 0;
  chunk->code = NULL;
}

void writeChunk(Chunk* chunk, u_int8_t byte) {
  if (chunk->capacity < chunk->count + 1) {
    // Grow array
    if (chunk->capacity == 0) {
      chunk->capacity = 8;
    } else {
      chunk->capacity = chunk->capacity * GROWTH_FACTOR;
    }

    const size_t old_size = sizeof(u_int8_t) * chunk->count;
    const size_t new_size = sizeof(u_int8_t) * chunk->capacity;

    chunk->code = reallocate(chunk->code, old_size, new_size);
  }

  chunk->code[chunk->count] = byte;
  chunk->count++;
}

void freeChunk(Chunk* chunk) {
  reallocate(chunk->code, sizeof(u_int8_t) * chunk->count, 0);
}
