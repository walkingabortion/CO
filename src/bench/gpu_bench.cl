__kernel void bench(__global const float* in, __global float* out, int n) {
    int i = get_global_id(0);
    if (i >= n) return;

    float x = in[i];

    float total = 0.0f;
    // for every float inside the input buffer we do 1000 cos and sin operations
    // for every cosine we do 1 extra Floating Point operation
    // so the total number of Floating point operations is size(in) * 1000 * 5 FP operations
    // (5 = 1 from 'x * j' + 1 from cos + 1 from sin + 1 from 'cos * sin' + 1 from 'total += ...'
    for (int j = 0; j < 1000; ++j) {
        total += cos(x * j) * sin((float) j);
    }
    out[i] = total;
}
