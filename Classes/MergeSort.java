public class MergeSort{

    public static void mergeSort(Patient[] a, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        Patient[] l = new Patient[mid];
        Patient[] r = new Patient[n - mid];

        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        mergeSort(l, mid);
        mergeSort(r, n - mid);

        merge(a, l, r, mid, n - mid);
    }

    public static void merge(Patient[] a, Patient[] l, Patient[] r, int left, int right) {

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i].urgencia < r[j].urgencia) {
                a[k++] = l[i++];
            }
            else if(l[i].urgencia > r[j].urgencia){
                a[k++] = r[j++];
            }
            else{
                if(l[i].idade == 3)
                    a[k++] = l[i++];

                else if(r[j].idade == 3)
                    a[k++] = r[j++];

                else if(l[i].idade == 1)
                    a[k++] = l[i++];

                else if(r[j].idade == 1)
                    a[k++] = r[j++];

                else
                    a[k++] = l[i++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
    }
}