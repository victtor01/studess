import { signal } from '@angular/core';

export interface ResourceState<T> {
  error: string | null;
  isLoading: boolean;
  data: T | null;
}

export function createResourceSignal<T>() {
  const s = signal<ResourceState<T>>({
    data: null,
    isLoading: false,
    error: null,
  });

  // Retornamos o Signal original, mas adicionamos métodos a ele
  return Object.assign(s, {
    setLoading: () => s.update((v) => ({ ...v, isLoading: true, error: null })),
    setSuccess: (data: T) => s.set({ data, isLoading: false, error: null }),
    setError: (error: string) => s.set({ data: null, isLoading: false, error }),
  });
}
